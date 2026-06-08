package com.example.book_management.service;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    public Book create(BookRequest book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new IllegalArgumentException(
                    "すでに登録されている本です: " + book.getTitle() + "/" + book.getAuthor()
            );
        }
        return bookRepository.save(Book.create(book.getTitle(), book.getAuthor(), book.getCategory()));
    }
}
