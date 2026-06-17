package com.example.book_management.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("ID: " + id + "が見つかりません");
    }

    public String getUserMessage() {
        return "指定された本は見つかりません";
    }
}
