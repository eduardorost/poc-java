package com.example.demo.services;

import com.example.demo.enums.Gender;
import com.example.demo.models.Person;
import com.example.demo.models.PersonReport;
import com.example.demo.repositories.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonReport buildReport(String filePath, String name1, String name2) {
        List<Person> people = personRepository.loadPeople(filePath);

        if (people.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        Optional<Person> person1 = findByName(people, name1);
        Optional<Person> person2 = findByName(people, name2);

        if (person1.isEmpty()) {
            throw new RuntimeException("Person not found: " + name1);
        }

        if (person2.isEmpty()) {
            throw new RuntimeException("Person not found: " + name2);
        }

        return PersonReport.builder()
                .maleCount(countByGender(people).getOrDefault(Gender.MALE, 0L))
                .person1(person1.get())
                .person2(person2.get())
                .oldestPerson(findOldestPerson(people))
                .ageDifference(compareAgeDifference(person1.get(), person2.get()))
                .build();
    }

    public Map<Gender, Long> countByGender(List<Person> people) {
        return people.stream().collect(Collectors.groupingBy(Person::getGender, Collectors.counting()));
    }

    public Optional<Person> findOldestPerson(List<Person> people) {
        return people.stream().min(Comparator.comparing(Person::getBirthDate));
    }

    public long compareAgeDifference(Person person1, Person person2) {
        return person1.getBirthDate().toEpochDay() - person2.getBirthDate().toEpochDay();
    }

    public Optional<Person> findByName(List<Person> people, String name) {
        return people.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
    }

}
