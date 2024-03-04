package com.itxn.libratrack.controller;

import com.itxn.libratrack.model.Book;
import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.services.BookService;
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


    private final BookService bookService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, PersonService personService) {
        this.bookService = bookService;
        this.personService = personService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookService.index());
        return "books/index";
    }

    @GetMapping (params = {"page", "books_per_page"})
    public String index(Model model, @RequestParam("page") Integer page, @RequestParam("books_per_page") Integer booksPerPage) {
        model.addAttribute("books", bookService.index(page, booksPerPage));
        return "books/index";
    }

    @GetMapping("{id}")
    public String show (@PathVariable("id") Integer id, Model model) {
        model.addAttribute("book", bookService.show(id));
        model.addAttribute("person", bookService.showHolder(id));
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
        bookService.create(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("book", bookService.show(id));
        return "books/edit";
    }

    @PostMapping("{id}")
    public String edit(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable Integer id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookService.edit(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("{id}/free_book")
    public String freeBook(@PathVariable Integer id) {
        bookService.freeBook(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("{id}/assign_person")
    public String assignPerson(Person person, @PathVariable Integer id) {
        bookService.assignPerson(person, id);
        return "redirect:/books/" + id;
    }
}
