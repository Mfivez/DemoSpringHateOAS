package com.example.demo.dal.repositories;

import com.example.demo.domain.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(
            "SELECT b " +
            "FROM Book b " +
            "WHERE b.title = :title")
    Optional<Book> findByTitle(String title);

}
