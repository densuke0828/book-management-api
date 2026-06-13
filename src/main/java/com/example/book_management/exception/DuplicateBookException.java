package com.example.book_management.exception;

public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException(String title, String author) {
        super(title + "/" + author + "は登録済みです");
    }

    public String getUserMessage() {
        return "指定された本は登録済みです";
    }
}
