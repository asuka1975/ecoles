package com.ecoles.online.ecoles.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoles.online.ecoles.controllers.bodies.BookBody;
import com.ecoles.online.ecoles.models.Book;
import com.ecoles.online.ecoles.services.BookService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @Autowired
    BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Book> getAllBooks() {
        return service.listAllBooks();
    }
    
    @PostMapping("")
    public Book postBook(@RequestBody BookBody body) {
        return service.postBook(body.getTitle());
    }

    @GetMapping("{bookId}")
    public Book getBook(@PathVariable int bookId) {
        return service.getBook(bookId);
    }
}
