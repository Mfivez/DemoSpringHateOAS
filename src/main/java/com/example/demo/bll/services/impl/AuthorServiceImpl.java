package com.example.demo.bll.services.impl;
import com.example.demo.api.controller.AuthorController;
import com.example.demo.api.hateOAS_Assembleur.AuthorResponseModelAssembler;
import com.example.demo.api.models.AuthorResponse;
import com.example.demo.api.models.UpdateAuthorRequest;
import com.example.demo.api.models.UpdateAuthorResponse;
import com.example.demo.bll.services.AuthorService;
import com.example.demo.dal.repositories.AuthorRepository;
import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public UpdateAuthorResponse updateAuthor(Long id, UpdateAuthorRequest request) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author doesn't exist"));

        author.setName(request.authorName());
        authorRepository.save(author);

        return new UpdateAuthorResponse("Author successfully updated");
    }


    private final AuthorResponseModelAssembler authorAssembler;

    /**
     * Méthode sans passer par l'assembleur, si nous devions faire ça
     * à chaque fois, cela deviendrait vite lourd et imbuvable en terme de
     * syntaxe, puisque la manière de procéder est souvent la même / très similaire,
     * nous allons plutôt créer une classe assembleur qui stockera la logique de la mise
     * sous modèleHateOAS.
     */
    @Override
    public CollectionModel<AuthorResponse> getAllAuthors() {
        CollectionModel<AuthorResponse> collectionModel = CollectionModel.of(
                authorRepository.findAll().stream()
                .map(author -> AuthorResponse.fromAuthor(author)
                        .add(linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel()))
                .collect(Collectors.toList())
        );
        collectionModel.add(linkTo(methodOn(AuthorController.class).getAllAuthor()).withSelfRel());

        return collectionModel;
    }

    /**
     *Mise en place et syntaxe beaucoup plus propre, on évite la répétition de code en rassemblant au même endroit
     * la logique d'ajout des liens.
     */
    @Override
    public AuthorResponse getAuthorById(Long id) {
        return authorAssembler.toModel(
                authorRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Author doesn't exist"))
        );
    }

    @Override
    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name)
                .orElseThrow(
                        () -> new RuntimeException("Author not found")
                );
    }

    @Override
    public Optional<Author> getOptionalAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author addAuthorByBookCreation(String name, Book book) {
        Author author = new Author();
        author.setName(name);
        author.setBooks(List.of(book));
        return authorRepository.save(author);
    }

}
