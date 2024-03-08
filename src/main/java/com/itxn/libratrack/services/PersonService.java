package com.itxn.libratrack.services;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.repositories.BookRepository;
import com.itxn.libratrack.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final BookRepository bookRepository;


    @Autowired
    public PersonService(PersonRepository personRepository, BookRepository bookRepository) {
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
    }

    public List<Person> index() {
        return personRepository.findAll();
    }

    public void create(Person person) {
        personRepository.save(person);
    }

    public Person show(int id) {
        return personRepository.findById(id).stream().findAny().orElse(null);
    }

    public Person show(String fullName) {
        return personRepository.findByFullName(fullName);
    }

    public void edit(int id, Person updatedPerson) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            person.setFullName(updatedPerson.getFullName());
            personRepository.save(person);
        }
    }

    public void delete(int id) {
        personRepository.deleteById(id);
    }

    public List<Book> listBooks(int id) {
        Person person = personRepository.findById(id).orElse(null);
        List<Book> books = bookRepository.findAllByPersonId(person.getId());
        if (!books.isEmpty()) {

            for (Book b : books) {
                if (b.getTakenTs() != null) {
                    LocalDate currDate = LocalDate.now();
                    if (b.getTakenTs().toEpochDay() < currDate.minusDays(10).toEpochDay()) {
                        b.setExpired(true);
                    }
                }
            }

             return books;
        }
        return null;
    }


}
