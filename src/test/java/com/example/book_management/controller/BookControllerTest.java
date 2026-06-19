package com.example.book_management.controller;

import com.example.book_management.dto.BookRequest;
import com.example.book_management.entity.Book;
import com.example.book_management.exception.BookNotFoundException;
import com.example.book_management.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

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
    void searchById_異常系_BookNotFoundExceptionが投げられる() throws Exception{
        given(bookService.searchById(1L)).willThrow(new BookNotFoundException(1L));

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
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

}
