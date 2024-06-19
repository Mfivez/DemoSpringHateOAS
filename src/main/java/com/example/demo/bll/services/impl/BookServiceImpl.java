package com.example.demo.bll.services.impl;

import com.example.demo.api.models.AddOrUpdateBookRequest;
import com.example.demo.api.models.AddOrUpdateBookResponse;
import com.example.demo.api.models.BookResponse;
import com.example.demo.api.models.DeleteResponse;
import com.example.demo.bll.services.AuthorService;
import com.example.demo.bll.services.BookService;
import com.example.demo.dal.repositories.BookRepository;
import com.example.demo.domain.entities.Author;
import com.example.demo.domain.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookResponse(
                        book.getTitle(),
                        book.getAuthors().stream()
                            .map(Author::getName)
                            .collect(Collectors.toList())))
                .toList();
    }

    @Override
    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> new BookResponse(
                        book.getTitle(),
                        book.getAuthors().stream()
                            .map(Author::getName)
                            .collect(Collectors.toList())))
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public AddOrUpdateBookResponse addBook(AddOrUpdateBookRequest request) {
        if (getBookByTitle(request.title()).isPresent()) {
            throw new RuntimeException("Book already exists");
        }

        Book book = new Book();
        book.setTitle(request.title());

        List<String> authorNames = verifyAuthorExists(request.authorNames(), book);
        book.setAuthors(getAuthors(authorNames));

        bookRepository.save(book);

        return new AddOrUpdateBookResponse("Book added successfully");
    }

    @Override
    public AddOrUpdateBookResponse updateBook(Long id, AddOrUpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        book.setTitle(request.title());

        List<String> authorNames = verifyAuthorExists(request.authorNames(), book);
        book.setAuthors(getAuthors(authorNames));

        bookRepository.save(book);

        return new AddOrUpdateBookResponse("Book updated successfully");
    }

    @Override
    public DeleteResponse deleteBook(Long id) {
        String message = "book " + getBookById(id).title() + " deleted successfully";
        bookRepository.deleteById(id);
        return new DeleteResponse(message);
    }

    private List<String> verifyAuthorExists(List<String> authorNames, Book book) {
        return authorNames.stream()
                .map(authorName -> authorService.getOptionalAuthorByName(authorName)
                        .orElseGet(() -> authorService.addAuthorByBookCreation(authorName, book))
                        .getName())
                .toList();
    }

    private List<Author> getAuthors(List<String> authorNames) {
        return authorNames.stream()
                .map(authorService::getAuthorByName)
                .collect(Collectors.toList());
    }

}
