package com.ecoles.online.ecoles.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecoles.online.ecoles.models.Book;
import com.ecoles.online.ecoles.repositories.BookRepository;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> listAllBooks() {
        return repository.listAllBooks();
    }
}
