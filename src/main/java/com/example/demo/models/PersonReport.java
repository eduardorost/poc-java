package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class PersonReport {

    private final long maleCount;
    private final Optional<Person> oldestPerson;
    private final Person person1;
    private final Person person2;
    private final long ageDifference;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("REPORT:");
        sb.append(System.lineSeparator()).append("Number of males: ").append(maleCount);
        oldestPerson.ifPresent(person -> sb.append(System.lineSeparator()).append("Oldest person: ").append(person));

        sb.append(System.lineSeparator());
        if (ageDifference == 0) {
            sb.append(String.format("%s and %s were born on the same day.", person1.getName(), person2.getName()));
        } else if (ageDifference < 0) {
            sb.append(String.format("%s is %s days older than %s.", person1.getName(), Math.abs(ageDifference), person2.getName()));
        } else {
            sb.append(String.format("%s is %s days younger than %s.", person1.getName(), ageDifference, person2.getName()));
        }

        return sb.toString();
    }


}
