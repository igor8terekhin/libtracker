package com.itxn.libratrack.repositories;

import com.itxn.libratrack.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findByFullName(String fullName);
}
