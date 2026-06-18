package com.example.book_management.controller;

import com.example.book_management.entity.Book;
import com.example.book_management.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void searchById_正常系_200が返る() throws Exception {
        Book book = Book.create("タイトル", "著者", "カテゴリ");
        given(bookService.searchById(1L)).willReturn(book);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("タイトル"));
    }
}
