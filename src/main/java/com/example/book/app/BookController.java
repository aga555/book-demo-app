package com.example.book.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Void> createNewBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        long id = bookService.createNewBook(bookRequest);
        UriComponents uriComponents = uriComponentsBuilder.path("/api/books/{id}").buildAndExpand(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());
        return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
    }

    ;

}
