package com.ecoles.online.ecoles.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ecoles.online.ecoles.exceptions.NotFoundException;
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
            .map(r -> new Book(r.getId(), r.getTitle(), r.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public Book insertBook(String title) {
        int id = jdbc.queryForObject("INSERT INTO book (title, created_at) VALUES (?, ?) RETURNING id", Integer.class, title, new Date());

        BookRecord record = getBookRecordById(id);
        
        return new Book(record.getId(), record.getTitle(), record.getCreatedAt());
    }

    private List<BookRecord> listAllBookRecords() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM book");
        List<BookRecord> records = new ArrayList<>();

        while(rs.next()) {
            records.add(new BookRecord(
                rs.getInt("id"), 
                rs.getString("title"),
                rs.getDate("created_at")));
        }
        return records;
    }

    private BookRecord getBookRecordById(int id) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM book WHERE id = ?", id);
        if(rs.next()) {
            return new BookRecord(id, rs.getString("title"), rs.getDate("created_at"));
        }
        throw new NotFoundException("");
    }
}
