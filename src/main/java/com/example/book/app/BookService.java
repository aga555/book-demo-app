package com.example.book.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public Long createNewBook(BookRequest bookRequest) {
        Book book = new Book();
        book.setAuthor(bookRequest.getAuthor());
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        book = bookRepository.save(book);

        return book.getId();

    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> requestBook = bookRepository.findById(id);
        if (!requestBook.isPresent()) {
            throw new BookNotFoundException("Book with this id not found" + id);
        }
        return requestBook.get();
    }

    @Transactional
    public Book updateBook(Long id, BookRequest bookToUpdateRequest) {

        Optional<Book> bookFromDatabase = bookRepository.findById(id);

        if (!bookFromDatabase.isPresent()) {
            throw new BookNotFoundException(String.format("Book with id: '%s' not found", id));
        }

        Book bookToUpdate = bookFromDatabase.get();

        bookToUpdate.setAuthor(bookToUpdateRequest.getAuthor());
        bookToUpdate.setIsbn(bookToUpdateRequest.getIsbn());
        bookToUpdate.setTitle(bookToUpdateRequest.getTitle());

        return bookToUpdate;
    }
}
