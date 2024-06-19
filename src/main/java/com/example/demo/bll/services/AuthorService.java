package com.example.demo.bll.services;

import com.example.demo.api.models.AuthorResponse;
import com.example.demo.api.models.UpdateAuthorRequest;
import com.example.demo.api.models.UpdateAuthorResponse;
import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import org.springframework.hateoas.CollectionModel;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    CollectionModel<AuthorResponse> getAllAuthors();

    AuthorResponse getAuthorById(Long id);

    Author getAuthorByName(String name);

    Optional<Author> getOptionalAuthorByName(String name);

    UpdateAuthorResponse updateAuthor(Long id, UpdateAuthorRequest request);

    Author addAuthorByBookCreation(String name, Book book);


}
