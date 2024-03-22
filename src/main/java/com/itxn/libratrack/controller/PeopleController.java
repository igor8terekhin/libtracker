package com.itxn.libratrack.controller;

import com.itxn.libratrack.model.Person;
import com.itxn.libratrack.services.PersonService;
import com.itxn.libratrack.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.index());
        return "people/index";
    }

    @GetMapping("/seeAll")
    @ResponseBody
    public List<Person> indexPeople(Model model) {
        return personService.index();
    }

    @GetMapping("/seePerson/{id}")
    @ResponseBody
    public Person showOnePerson(@PathVariable("id") int id) {
        return personService.show(id);
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.show(id));
        model.addAttribute("books", personService.listBooks(id));
        return "people/show";
    }

    @GetMapping("/create")
    public String createNew(@ModelAttribute("person") Person person) {
        return "people/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/create";
        personService.create(person);
        return "redirect:people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable int id) {
        model.addAttribute("person", personService.show(id));
        return "people/edit";
    }
    @PostMapping("/{id}")
    public String edit( @ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable int id) {
        if (!(personService.show(id).getFullName()).equals(person.getFullName())) {
            personValidator.validate(person, bindingResult);
            if (bindingResult.hasErrors())
                return "people/edit";
        }
        personService.edit(id, person);
        return "redirect:../people";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
