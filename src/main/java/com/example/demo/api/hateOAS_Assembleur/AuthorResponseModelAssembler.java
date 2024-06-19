package com.example.demo.api.hateOAS_Assembleur;

import com.example.demo.api.controller.AuthorController;
import com.example.demo.api.models.AuthorResponse;
import com.example.demo.api.models.BookResponse;
import com.example.demo.domain.entities.Author;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthorResponseModelAssembler extends RepresentationModelAssemblerSupport<Author, AuthorResponse> {

        public AuthorResponseModelAssembler() {
            super(AuthorController.class, AuthorResponse.class);
        }

    @Override
    public AuthorResponse toModel(Author author) {
        AuthorResponse authorResponse = AuthorResponse.fromAuthor(author);
        authorResponse.add(linkTo(methodOn(AuthorController.class).getAuthorById(author.getId())).withSelfRel());
        authorResponse.add(linkTo(methodOn(AuthorController.class).updateAuthor(author.getId(), null)).withRel("update"));
        authorResponse.add(linkTo(methodOn(AuthorController.class).getAllAuthor()).withRel("get-all"));
        return authorResponse;
    }

    @Override
    public CollectionModel<AuthorResponse> toCollectionModel(Iterable<? extends Author> entities) {
        CollectionModel<AuthorResponse> authorResponses = super.toCollectionModel(entities);
        authorResponses.add(linkTo(methodOn(AuthorController.class).getAllAuthor()).withSelfRel());
        return authorResponses;
    }
}