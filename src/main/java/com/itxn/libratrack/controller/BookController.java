package com.itxn.libratrack.controller;

import com.itxn.libratrack.dao.BookDAO;
import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("books")
public class BookController {


    private final BookDAO bookDAO;
    private final PersonService personService;

    @Autowired
    public BookController(BookDAO bookDAO, PersonService personService) {
        this.bookDAO = bookDAO;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("person", bookDAO.showHolder(id));
        model.addAttribute("people", personService.index());
        model.addAttribute("persona", new Person());
        return "books/show";
    }

    @GetMapping("/create")
    public String create (Model model, Book book) {
        return "books/create";
    }

    @PostMapping
    public String create (@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/create";
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PostMapping("{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.edit(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PostMapping("{id}/free_book")
    public String freeBook(@PathVariable int id) {
        bookDAO.freeBook(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("{id}/assign_person")
    public String assignPerson(Person person, @PathVariable int id) {
        bookDAO.assignPerson(person, id);
        return "redirect:/books/" + id;
    }
}
