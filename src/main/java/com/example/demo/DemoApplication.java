package com.example.demo;

import com.example.demo.models.PersonReport;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String filePath = "data.txt";
        String name2 = "Bill McKnight";
        String name1 = "Wes Jackson";

        if (args.length == 3) {
            filePath = args[0];
            name2 = args[1];
            name1 = args[2];
        }

        PersonReport personReport = personService.buildReport(filePath, name1, name2);
        System.out.println(personReport);
    }
}
