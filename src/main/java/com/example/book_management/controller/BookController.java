package com.example.book_management.controller;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.dto.BookResponse;
import com.example.book_management.entity.Book;
import com.example.book_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Validated @RequestBody BookRequest request) {
        Book book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookResponse.from(book));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll() {
        List<BookResponse> books = bookService.findAll()
                .stream()
                .map(BookResponse::from)
                .toList();
        return ResponseEntity.ok(books);
    }
}
