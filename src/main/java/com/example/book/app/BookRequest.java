package com.example.book.app;

import lombok.Data;


@Data
public class BookRequest {

    private String title;

    private String isbn;

    private String author;
}
