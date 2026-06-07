package com.example.book_management.service;

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
    private String create(String title, String author, String category) {
        if (bookRepository.existsByTitleAndAuthor(title, author)) {
            throw new IllegalArgumentException(
                    "すでに登録されている本です: " + title + "/" + author
            );
        }
        bookRepository.save(Book.create(title, author, category));
        return "新しい本を登録しました。";
    }
}
