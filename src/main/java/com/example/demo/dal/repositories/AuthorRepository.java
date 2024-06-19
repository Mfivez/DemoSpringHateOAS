package com.example.demo.dal.repositories;

import com.example.demo.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(
            "SELECT a " +
            "FROM Author a " +
            "WHERE a.name = :name" )
    Optional<Author> findByName(String name);
}
