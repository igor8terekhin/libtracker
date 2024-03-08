package com.itxn.libratrack.services;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.repositories.BookRepository;
import com.itxn.libratrack.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Book> index(String sortBy) {
        return bookRepository.findAll(Sort.by(sortBy));
    }

    public List<Book> index(Integer page, Integer booksPerPage, String sortBy) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by(sortBy))).getContent();
    }

    public List<Book> index(Integer page, Integer booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
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
        bookRepository.setTakenTime(id);
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContains(title);
    }

    public List<Person> showHolderByBooks(List<Book> bookList) {
        List<Person> people = personRepository.findAll();

      return people.stream()
              .filter(person -> person != null && bookList.stream()
                      .anyMatch(book -> book != null && book.getPersonId() != null && book.getPersonId().equals(person.getId())))
              .collect(Collectors.toList());
    }
}
