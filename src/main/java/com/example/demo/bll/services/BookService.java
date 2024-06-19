package com.example.demo.bll.services;

import com.example.demo.api.models.AddOrUpdateBookResponse;
import com.example.demo.api.models.AddOrUpdateBookRequest;
import com.example.demo.api.models.BookResponse;
import com.example.demo.api.models.DeleteResponse;
import com.example.demo.domain.entities.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookResponse> getAllBooks();

    BookResponse getBookById(Long id);

    Optional<Book> getBookByTitle(String title);

    AddOrUpdateBookResponse addBook(AddOrUpdateBookRequest request);

    AddOrUpdateBookResponse updateBook(Long id, AddOrUpdateBookRequest request);

    DeleteResponse deleteBook(Long id);

}
