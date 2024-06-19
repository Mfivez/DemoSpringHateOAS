package com.example.demo.api.models;
import com.example.demo.domain.entities.Author;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class AuthorResponse extends RepresentationModel<AuthorResponse> {
    private final String name;

    public AuthorResponse(String name) {
        this.name = name;
    }

    public static AuthorResponse fromAuthor(Author author) {
        return new AuthorResponse(author.getName());
    }
}
