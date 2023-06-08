package com.ecoles.online.ecoles.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoles.online.ecoles.models.Book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/books")
public class BookController {
    @GetMapping("")
    public List<Book> getAllBooks() {
        return new ArrayList<>();
    }
    
}
