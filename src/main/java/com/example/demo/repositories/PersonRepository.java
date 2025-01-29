package com.example.demo.repositories;

import com.example.demo.enums.Gender;
import com.example.demo.models.Person;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PersonRepository {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");

    public List<Person> loadPeople(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            return lines.stream().map(this::parseLine).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    private Person parseLine(String line) {
        String[] lineParts = line.split(",");
        return Person.builder()
                .name(lineParts[0].trim())
                .gender(Gender.valueOf(lineParts[1].trim().toUpperCase()))
                .birthDate(LocalDate.parse(lineParts[2].trim(), FORMATTER).minusYears(100))
                .build();
    }


}
