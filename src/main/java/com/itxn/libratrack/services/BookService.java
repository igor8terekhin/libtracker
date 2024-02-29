package com.itxn.libratrack.services;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.repositories.BookRepository;
import com.itxn.libratrack.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public void create(Book book) {
        bookRepository.save(book);
    }

    public Book show(int id) {
        return bookRepository.findById(id).stream().findAny().orElse(null);
    }

    public void edit(int id, Book updatedBook) {
        Book book = bookRepository.findById(id).orElse(null);

        if (book != null) {
            book.setAuthor(updatedBook.getAuthor());
            book.setTitle(updatedBook.getTitle());
            book.setYear(updatedBook.getYear());
            bookRepository.save(book);
        }
    }

    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public Person showHolder(Integer id) {
        Book book = bookRepository.findById(id).stream().findAny().orElse(null);
        if (book != null) {
            if (book.getPersonId() == null)
                return null;
            else
                return personRepository.findById(book.getPersonId()).stream().findAny().orElse(null);
        }
        else
            return null;
    }

    public void freeBook(int id) {
        bookRepository.freeBook(id);
    }

    public void assignPerson(Person person, int id) {
        bookRepository.assignPerson(person.getId(), id);
    }
}
