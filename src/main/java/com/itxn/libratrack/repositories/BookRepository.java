package com.itxn.libratrack.repositories;

import com.itxn.libratrack.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Modifying
    @Query(value = "UPDATE book SET person_id = null WHERE id = ?", nativeQuery = true)
    void freeBook(int id);

    @Modifying
    @Query(value = "UPDATE book SET person_id = ? WHERE id = ?", nativeQuery = true)
    void assignPerson(int personId, int bookId);

    List<Book> findAllByPersonId(Integer personId);

    List<Book> findByTitleContains(String title);
}
