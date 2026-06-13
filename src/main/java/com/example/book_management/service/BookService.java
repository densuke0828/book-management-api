package com.example.book_management.service;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.exception.BookNotFoundException;
import com.example.book_management.exception.DuplicateBookException;
import com.example.book_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book searchById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Transactional(readOnly = false)
    public Book createBook(BookRequest book) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            throw new DuplicateBookException(book.getTitle(), book.getAuthor());
        }
        return bookRepository.save(Book.create(book.getTitle(), book.getAuthor(), book.getCategory()));
    }

    @Transactional(readOnly = false)
    public Book updateBook(Long id, BookRequest book) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        foundBook.update(book.getTitle(), book.getAuthor(), book.getCategory());
        return foundBook;
    }

    @Transactional(readOnly = false)
    public void deleteBook(Long id) {
        Book foundBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(foundBook);
    }
}
