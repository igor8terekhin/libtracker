package com.itxn.libratrack.dao;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT id, title, author, year FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book (title, author, year) VALUES (?, ?, ?)", book.getTitle(),
                book.getAuthor(), book.getYear());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT id, title, author, year FROM book WHERE id=?", new Object[] {id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void edit(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, year = ? WHERE id = ?", updatedBook.getTitle(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete (int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public Person showHolder(int id) {
        return jdbcTemplate.query("SELECT * FROM person p JOIN book b on p.id = b.person_id WHERE b.id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("UPDATE book SET person_id = null WHERE id = ?", id);
    }
}
