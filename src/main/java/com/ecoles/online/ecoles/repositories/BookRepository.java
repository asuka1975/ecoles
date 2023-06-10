package com.ecoles.online.ecoles.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ecoles.online.ecoles.models.Book;
import com.ecoles.online.ecoles.repositories.records.BookRecord;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    BookRepository(JdbcTemplate jdbcTemplate) {
        jdbc = jdbcTemplate;
    }

    public List<Book> listAllBooks() {
        return listAllBookRecords()
            .stream()
            .map(r -> new Book(r.getId(), r.getTitle()))
            .collect(Collectors.toList());
    }

    private List<BookRecord> listAllBookRecords() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM book");
        List<BookRecord> records = new ArrayList<>();

        while(rs.next()) {
            records.add(new BookRecord(
                rs.getLong("id"), 
                rs.getString("title")));
        }
        return records;
    }
}
