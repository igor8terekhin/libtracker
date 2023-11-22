package com.itxn.libratrack.model;

public class Person {

    private int id;
    private int birthYear;
    private String fullName;

    public Person() {

    }

    public Person(int id, int birthYear, String fullName) {
        this.id = id;
        this.birthYear = birthYear;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
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
