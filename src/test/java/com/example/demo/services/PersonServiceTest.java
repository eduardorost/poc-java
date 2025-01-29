package com.example.demo.services;

import com.example.demo.enums.Gender;
import com.example.demo.models.Person;
import com.example.demo.models.PersonReport;
import com.example.demo.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {

    @Spy
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    private final Person MOCK_PERSON1 = Person.builder().name("Alice").gender(Gender.FEMALE).birthDate(LocalDate.of(1990, 1, 1)).build();
    private final Person MOCK_PERSON2 = Person.builder().name("Bob").gender(Gender.MALE).birthDate(LocalDate.of(1985, 5, 10)).build();

    private final List<Person> MOCK_PEOPLE = List.of(
            MOCK_PERSON1,
            MOCK_PERSON2,
            Person.builder().name("Charlie").gender(Gender.MALE).birthDate(LocalDate.of(2000, 3, 15)).build()
    );

    @Test
    void countByGender() {
        Map<Gender, Long> countByGender = personService.countByGender(MOCK_PEOPLE);
        assertEquals(2L, countByGender.get(Gender.MALE));
    }

    @Test
    void findOldestPerson() {
        Optional<Person> oldestPerson = personService.findOldestPerson(MOCK_PEOPLE);
        assertEquals("Bob", oldestPerson.get().getName());
    }

    @Test
    void compareAgeDifference_Older() {
        long ageDifference = personService.compareAgeDifference(MOCK_PERSON1, MOCK_PERSON2);
        System.out.println(ageDifference);
        assertEquals(1697L, ageDifference);
    }

    @Test
    void compareAgeDifference_Younger() {
        long ageDifference = personService.compareAgeDifference(MOCK_PERSON2, MOCK_PERSON1);
        System.out.println(ageDifference);
        assertEquals(-1697L, ageDifference);
    }

    @Test
    void findByName() {
        Optional<Person> bob = personService.findByName(MOCK_PEOPLE, "Bob");
        assertEquals(MOCK_PERSON2, bob.get());
    }


    @Test
    void buildReport_emptyPeople_shouldThrowRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            personService.buildReport("", "", "");
        });
    }

    @Test
    void buildReport_wrongPerson1_shouldThrowRuntimeException() {
        when(personRepository.loadPeople(any())).thenReturn(MOCK_PEOPLE);

        assertThrows(RuntimeException.class, () -> {
            personService.buildReport("", "test", "");
        });
    }

    @Test
    void buildReport_wrongPerson2_shouldThrowRuntimeException() {
        when(personRepository.loadPeople(any())).thenReturn(MOCK_PEOPLE);

        assertThrows(RuntimeException.class, () -> {
            personService.buildReport("", "Bob", "test");
        });
    }

    @Test
    void buildReport() {
        when(personRepository.loadPeople(any())).thenReturn(MOCK_PEOPLE);
        PersonReport personReport = personService.buildReport("", "Alice", "Bob");

        assertEquals(2L, personReport.getMaleCount());
        assertEquals(MOCK_PERSON2, personReport.getOldestPerson().get());
        assertEquals(1697L, personReport.getAgeDifference());
        assertEquals(MOCK_PERSON1, personReport.getPerson1());
        assertEquals(MOCK_PERSON2, personReport.getPerson2());
    }
}