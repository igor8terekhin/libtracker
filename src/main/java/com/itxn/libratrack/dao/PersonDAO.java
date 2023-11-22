package com.itxn.libratrack.dao;

import com.itxn.libratrack.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, 20, "John Wick"));
        people.add(new Person(++PEOPLE_COUNT, 25, "Alister Crowley"));
        people.add(new Person(++PEOPLE_COUNT, 30, "Max Katz"));
        people.add(new Person(++PEOPLE_COUNT, 35, "Michael Schumaher"));
    }

    public List<Person> index() {
        return people;
    }
}
