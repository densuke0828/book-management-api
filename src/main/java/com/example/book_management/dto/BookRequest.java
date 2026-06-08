package com.example.book_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookRequest {
    @NotBlank(message = "タイトルは必須です")
    private String title;

    @NotBlank(message = "著者は必須です")
    private String author;

    private String category;
}
