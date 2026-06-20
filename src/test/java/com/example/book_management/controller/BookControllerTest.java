package com.example.book_management.controller;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.exception.BookNotFoundException;
import com.example.book_management.exception.DuplicateBookException;
import com.example.book_management.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willThrow;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * searchById
     */
    @Test
    void searchById_正常系_200が返る() throws Exception {
        Book book = Book.create("タイトル", "著者", "カテゴリ");
        given(bookService.searchById(1L)).willReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("タイトル"));
    }
    @Test
    void searchById_異常系_404が返る() throws Exception{
        given(bookService.searchById(1L)).willThrow(new BookNotFoundException(1L));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * findAll
     */
    @Test
    void findAll_正常系_200が返る() throws Exception {
        Book book1 = Book.create("タイトル1", "著者1", "カテゴリ1");
        Book book2 = Book.create("タイトル2", "著者2", "カテゴリ2");
        given(bookService.findAll()).willReturn(List.of(book1, book2));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("タイトル1"))
                .andExpect(jsonPath("$[1].title").value("タイトル2"))
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    void findAll_正常系_空のリストが返る() throws Exception {
        given(bookService.findAll()).willReturn(List.of());

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     * createBook
     */
    @Test
    void createBook_正常系_201が返る() throws Exception {
        BookRequest request = new BookRequest("タイトル", "著者", "カテゴリ");
        Book book = Book.create(request.getTitle(), request.getAuthor(), request.getCategory());
        String json = objectMapper.writeValueAsString(request);
        given(bookService.createBook(any(BookRequest.class))).willReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("タイトル"));
    }
    @Test
    void createBook_異常系_409が返る() throws Exception {
        BookRequest request = new BookRequest("タイトル", "著者", "カテゴリ");
        String json = objectMapper.writeValueAsString(request);
        given(bookService.createBook(any(BookRequest.class)))
                .willThrow(new DuplicateBookException(request.getTitle(), request.getAuthor()));

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }

    /**
     * updateBook
     */
    @Test
    void updateBook_正常系_200が返る() throws Exception {
        BookRequest request = new BookRequest("新タイトル", "新著者", "新カテゴリ");
        Book book = Book.create(request.getTitle(), request.getAuthor(), request.getCategory());
        String json = objectMapper.writeValueAsString(request);
        given(bookService.updateBook(anyLong(), any(BookRequest.class))).willReturn(book);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("新タイトル"));
    }
    @Test
    void updateBook_異常系_404が返る() throws Exception {
        BookRequest request = new BookRequest("新タイトル", "新著者", "新カテゴリ");
        String json = objectMapper.writeValueAsString(request);
        given(bookService.updateBook(anyLong(), any(BookRequest.class)))
                .willThrow(new BookNotFoundException(1L));

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

    }

    /**
     * deleteBook
     */
    @Test
    void deleteBook_正常系_204が返る() throws Exception {
        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNoContent());
    }
    @Test
    void deleteBook_異常系_404が返る() throws Exception {
        willThrow(new BookNotFoundException(1L)).given(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isNotFound());
    }

}
