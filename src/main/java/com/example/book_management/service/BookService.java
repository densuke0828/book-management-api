package com.example.book_management.service;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Book createBook(BookRequest book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new IllegalArgumentException(
                    "すでに登録されている本です: " + book.getTitle() + "/" + book.getAuthor()
            );
        }
        return bookRepository.save(Book.create(book.getTitle(), book.getAuthor(), book.getCategory()));
    }
}
