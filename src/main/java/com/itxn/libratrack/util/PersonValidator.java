package com.itxn.libratrack.util;

import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personDAO) {
        this.personService = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (personService.show(person.getFullName())!=null)
            errors.rejectValue("fullName", "", "This person already exists!");
    }
}
