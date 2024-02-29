package com.itxn.libratrack.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Min(value = 1900, message = "Birth year cannot be earlier than 1900")
    private int birthYear;
    @NotEmpty (message = "Name shouldn't be empty!")
    @Size(min = 2, max = 100, message = "Name should have at least 2 characters!")
    private String fullName;

    public Person() {

    }

    public Person(int id, int birthYear, String fullName) {
        this.id = id;
        this.birthYear = birthYear;
        this.fullName = fullName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
