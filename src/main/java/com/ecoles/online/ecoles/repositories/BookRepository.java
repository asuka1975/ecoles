package com.ecoles.online.ecoles.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.HashMap;

import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.ecoles.online.ecoles.exceptions.NotFoundException;
import com.ecoles.online.ecoles.models.Author;
import com.ecoles.online.ecoles.models.Book;
import com.ecoles.online.ecoles.repositories.records.BookDetailRecord;
import com.ecoles.online.ecoles.repositories.records.BookRecord;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbc;

    @Autowired
    BookRepository(JdbcTemplate jdbcTemplate) {
        jdbc = jdbcTemplate;
    }

    public List<Book> listAllBooks() {
        List<BookDetailRecord> bookDetails = listAllBookDetails();
        Map<Integer, Author> authors = listAllAuthors()
            .stream()
            .collect(Collectors.toMap(
                Author::getId,
                UnaryOperator.identity()
            ));

        Map<Integer, List<Author>> authorsWithBookId = bookDetails
            .stream()
            .collect(Collectors.groupingBy(BookDetailRecord::getBookId))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                e -> e.getKey(),
                e -> e.getValue()
                    .stream()
                    .map(r -> authors.get(r.getAuthorId()))
                    .collect(Collectors.toList())
            ));

        return listAllBookRecords()
            .stream()
            .map(r -> new Book(r.getId(), r.getTitle(), r.getCreatedAt(), authorsWithBookId.get(r.getId())))
            .collect(Collectors.toList());
    }

    public Book getBook(int bookId) {
        BookRecord bookRecord = getBookRecordById(bookId);
        List<Author> authors = listAuthorsByBookId(bookId);

        return new Book(bookRecord.getId(), bookRecord.getTitle(), bookRecord.getCreatedAt(), authors);
    }

    public Book insertBook(String title) {
        int id = jdbc.queryForObject("INSERT INTO book (title, created_at) VALUES (?, ?) RETURNING id", Integer.class, title, new Date());

        BookRecord record = getBookRecordById(id);
        List<Author> authors = listAuthorsByBookId(id);
        
        return new Book(record.getId(), record.getTitle(), record.getCreatedAt(), authors);
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

    private List<Author> listAuthorsByBookId(int bookId) {
        List<Author> authors = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT id, name FROM author JOIN (SELECT author_id FROM book_detail WHERE book_id = ?) AS author_info ON author.id = author_info.author_id;", bookId);
        while(rs.next()) {
            authors.add(
                new Author(
                    rs.getInt("id"), 
                    rs.getString("name"))
            );
        }

        return authors;
    }

    private List<Author> listAllAuthors() {
        List<Author> authors = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM author");
        while(rs.next()) {
            authors.add(
                new Author(
                    rs.getInt("id"), 
                    rs.getString("name"))
            );
        }

        return authors;
    }

    private List<BookDetailRecord> listAllBookDetails() {
        List<BookDetailRecord> bookDetails = new ArrayList<>();
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM book_detail");
        while(rs.next()) {
            bookDetails.add(
                new BookDetailRecord(
                    rs.getInt("book_id"), 
                    rs.getInt("author_id"))
            );
        }

        return bookDetails;
    }
}
