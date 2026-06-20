package com.example.book_management.service;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.exception.BookNotFoundException;
import com.example.book_management.exception.DuplicateBookException;
import com.example.book_management.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    /**
     * searchById
     */
    @Test
    void searchById_正常系_本が返ってくる() {
        Book book = Book.create("タイトル", "著者", "カテゴリ");
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));

        Book result = bookService.searchById(1L);

        assertThat(result.getTitle()).isEqualTo("タイトル");
        assertThat(result.getAuthor()).isEqualTo("著者");
        assertThat(result.getCategory()).isEqualTo("カテゴリ");
    }
    @Test
    void searchById_異常系_BookNotFoundExceptionが投げられる() {
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.searchById(1L))
                .isInstanceOf(BookNotFoundException.class);
    }

    /**
     * findAll
     */
    @Test
    void findAll_正常系_本のリストが返ってくる() {
        Book book1 = Book.create("タイトル1", "著者1", "カテゴリ1");
        Book book2 = Book.create("タイトル2", "著者2", "カテゴリ2");
        given(bookRepository.findAll()).willReturn(List.of(book1, book2));

        List<Book> result = bookService.findAll();

        assertThat(result.get(0).getTitle()).isEqualTo("タイトル1");
        assertThat(result.get(1).getTitle()).isEqualTo("タイトル2");
        assertThat(result).hasSize(2);
    }
    @Test
    void findAll_正常系_空のリストが返ってくる() {
        given(bookRepository.findAll()).willReturn(List.of());

        List<Book> result = bookService.findAll();

        assertThat(result).isEmpty();
    }

    /**
     * createBook
     */
    @Test
    void createBook_正常系_本が登録される() {
        BookRequest request = new BookRequest("タイトル", "著者", "カテゴリ");
        Book book = Book.create(request.getTitle(), request.getAuthor(), request.getCategory());
        given(bookRepository.existsByTitleAndAuthor(request.getTitle(), request.getAuthor())).willReturn(false);
        given(bookRepository.save(any(Book.class))).willReturn(book);

        Book result = bookService.createBook(request);

        assertThat(result.getTitle()).isEqualTo("タイトル");
        assertThat(result.getAuthor()).isEqualTo("著者");
        assertThat(result.getCategory()).isEqualTo("カテゴリ");

    }
    @Test
    void createBook_異常系_DuplicateBookExceptionが投げられる() {
        BookRequest request = new BookRequest("タイトル", "著者" , "カテゴリ");
        given(bookRepository.existsByTitleAndAuthor(request.getTitle(), request.getAuthor())).willReturn(true);

        assertThatThrownBy(() -> bookService.createBook(request))
                .isInstanceOf(DuplicateBookException.class);

    }

    /**
     * updateBook
     */
    @Test
    void updateBook_正常系_本の情報が更新される() {
        BookRequest request = new BookRequest("新タイトル", "新著者", "新カテゴリ");
        Book foundBook = Book.create("タイトル", "著者", "カテゴリ");
        given(bookRepository.findById(1L)).willReturn(Optional.of(foundBook));

        Book result = bookService.updateBook(1L, request);

        assertThat(result.getTitle()).isEqualTo("新タイトル");
        assertThat(result.getAuthor()).isEqualTo("新著者");
        assertThat(result.getCategory()).isEqualTo("新カテゴリ");
    }
    @Test
    void updateBook_異常系_BookNotFoundExceptionが投げられる() {
        BookRequest request = new BookRequest("新タイトル", "新著者", "新カテゴリ");
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.updateBook(1L, request))
                .isInstanceOf(BookNotFoundException.class);
    }

    /**
     * deleteBook
     */
    @Test
    void deleteBook_正常系_本が削除される() {
        Book book = Book.create("タイトル", "著者", "カテゴリ");
        given(bookRepository.findById(1L)).willReturn(Optional.of(book));

        bookService.deleteBook(1L);

        then(bookRepository).should().delete(book);
    }
    @Test
    void deleteBook_異常系_BookNotFoundExceptionが投げられる() {
        given(bookRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() ->bookService.deleteBook(1L))
                .isInstanceOf(BookNotFoundException.class);
    }
}
