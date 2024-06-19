package com.example.demo.api.controller;

import com.example.demo.api.models.*;
import com.example.demo.bll.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/get-all")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<AddOrUpdateBookResponse> addBook(@RequestBody AddOrUpdateBookRequest b) {
        return ResponseEntity.ok(bookService.addBook(b));
    }

    @PutMapping("/update-by-id/{id}")
    public ResponseEntity<AddOrUpdateBookResponse> updateBook(@PathVariable Long id, @RequestBody AddOrUpdateBookRequest b) {
        return ResponseEntity.ok(bookService.updateBook(id, b));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<DeleteResponse> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

}
