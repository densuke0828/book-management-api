package com.example.book_management.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReadingStatus {
    UNREAD("未読"),
    READING("読書中"),
    COMPLETED("読了");

    private final String displayName;
}
