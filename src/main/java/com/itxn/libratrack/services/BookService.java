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
@Transactional(readOnly = false)
public class BookService {

    private final BookRepository br;
    private final PersonRepository pr;

    @Autowired
    public BookService(BookRepository br, PersonRepository pr) {
        this.br = br;
        this.pr = pr;
    }

    public List<Book> index() {
        return br.findAll();
    }

    public void create(Book book) {
        br.save(book);
    }

    public Book show(int id) {
        return br.findById(id).stream().findAny().orElse(null);
    }

    public void edit(int id, Book updatedBook) {
        Book book = br.findById(id).orElse(null);

        if (book != null) {
            book.setAuthor(updatedBook.getAuthor());
            book.setTitle(updatedBook.getTitle());
            book.setYear(updatedBook.getYear());
            br.save(book);
        }
    }

    public void delete(int id) {
        br.deleteById(id);
    }

    public Person showHolder(Integer id) {
        Book book = br.findById(id).stream().findAny().orElse(null);
        if (book != null) {
            if (book.getPersonId() == null)
                return null;
            else
                return pr.findById(book.getPersonId()).stream().findAny().orElse(null);
        }
        else
            return null;
    }

    public void freeBook(int id) {
        br.freeBook(id);
    }

    public void assignPerson(Person person, int id) {
        br.assignPerson(person.getId(), id);
    }
}
