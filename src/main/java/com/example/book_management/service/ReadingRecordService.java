package com.example.book_management.service;

import com.example.book_management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadingRecordService {
    private final BookRepository bookRepository;
    private String create(String title, String status, String memo) {
        if ()
    }
}
