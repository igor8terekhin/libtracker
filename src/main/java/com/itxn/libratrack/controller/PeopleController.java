package com.itxn.libratrack.controller;

import com.itxn.libratrack.dao.PersonDAO;
import com.itxn.libratrack.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "people/show";
    }

    @GetMapping("/create")
    public String createNew(@ModelAttribute("person") Person person) {
        return "people/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.create(person);
        return "redirect:people";
    }

}
