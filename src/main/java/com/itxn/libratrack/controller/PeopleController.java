package com.itxn.libratrack.controller;

import com.itxn.libratrack.dao.PersonDAO;
import com.itxn.libratrack.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books", personDAO.listBooks(id));
        return "people/show";
    }

    @GetMapping("/create")
    public String createNew(@ModelAttribute("person") Person person) {
        return "people/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/create";
        personDAO.create(person);
        return "redirect:people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }
    @PostMapping("/{id}")
    public String edit( @ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.edit(id, person);
        return "redirect:../people";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
