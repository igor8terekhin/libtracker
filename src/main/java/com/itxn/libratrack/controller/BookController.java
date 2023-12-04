package com.itxn.libratrack.controller;

import com.itxn.libratrack.dao.BookDAO;
import com.itxn.libratrack.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("books")
public class BookController {


    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/show";
    }

    @GetMapping("/create")
    public String create (Model model, Book book) {
        return "books/create";
    }

    @PostMapping
    public String create (@ModelAttribute("book") Book book) {
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit (Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PostMapping("{id}")
    public String edit (@ModelAttribute("book") Book book, @PathVariable int id) {
        bookDAO.edit(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete (@PathVariable int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }


}
