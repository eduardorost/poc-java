package com.example.demo.models;

import com.example.demo.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Person {

    private final String name;
    private final Gender gender;
    private final LocalDate birthDate;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                '}';
    }
}
