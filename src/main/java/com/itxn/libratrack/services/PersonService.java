package com.itxn.libratrack.services;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
@Service
@Transactional(readOnly = false)
public class PersonService {
    private final PersonRepository pr;

    private final EntityManager em;

    @Autowired
    public PersonService(PersonRepository pr, EntityManager em) {
        this.pr = pr;
        this.em = em;
    }

    public List<Person> index() {
        return pr.findAll();
    }

    public void create(Person person) {
        pr.save(person);
    }

    public Person show(int id) {
        return pr.findById(id).stream().findAny().orElse(null);
    }

    public Person show(String fullName) {
        return pr.findByFullName(fullName);
    }

    public void edit(int id, Person updatedPerson) {
        Person person = pr.findById(id).orElse(null);
        if (person != null) {
            person.setFullName(updatedPerson.getFullName());
            pr.save(person);
        }
    }

    public void delete(int id) {
        pr.deleteById(id);
    }

    public List<Book> listBooks(int id) {
        Person person = pr.findById(id).orElse(null);
        List<Book> books;
        if (person != null) {
             return em.createNativeQuery("SELECT b.title, b.author, b.year FROM book b WHERE b.person_id = :id", Book.class).getResultList();
        }
        return null;
    }


}
