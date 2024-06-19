package com.example.demo.api.controller;
import com.example.demo.api.models.AuthorResponse;
import com.example.demo.api.models.UpdateAuthorRequest;
import com.example.demo.api.models.UpdateAuthorResponse;
import com.example.demo.bll.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/get-all")
    public ResponseEntity<CollectionModel<AuthorResponse>> getAllAuthor() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<UpdateAuthorResponse> updateAuthor(@PathVariable Long id, @RequestBody UpdateAuthorRequest a) {
        return ResponseEntity.ok(authorService.updateAuthor(id, a));
    }

}
