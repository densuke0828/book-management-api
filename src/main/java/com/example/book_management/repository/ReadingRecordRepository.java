package com.example.book_management.repository;

import com.example.book_management.entity.ReadingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRecordRepository extends JpaRepository<ReadingRecord, Long> {
}
