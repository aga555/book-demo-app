package com.example.book.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Captor
    private ArgumentCaptor<BookRequest> bookRequestArgumentCaptor;


    @Test
    void postingANewBookShouldCreateANewBookInTheDatabase() throws Exception {
        //given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Aga");
        bookRequest.setIsbn("1212121212");
        bookRequest.setTitle("aaaad");
        //when
        when(bookService.createNewBook(bookRequestArgumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/api/books/1"));
        //then
        assertThat(bookRequestArgumentCaptor.getValue().getAuthor(), is("Aga"));
    }

    @Test
    public void allBooksShouldReturnTwoBooks() throws Exception {
        //given

        Book book1;
        Book book2;
        book1 = createBook(1L, "Java", "aga", "111");
        book2 = createBook(2L, "Java8", "baba", "11www1");
        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        when(bookService.getAllBooks()).thenReturn(bookList);
        this.mockMvc
                .perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Java")))
                .andExpect(jsonPath("$[0].author", is("aga")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Java8")))
                .andExpect(jsonPath("$[1].author", is("baba")))
                .andExpect(jsonPath("$[1].id", is(2)));

    }

    @Test
    public void WithIdOneShouldReturnBook() throws Exception {

        Book book;
        book = createBook(1L, "Java", "aga", "111");

        when(bookService.getBookById(1L)).thenReturn(book);


        this.mockMvc
                .perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))

                .andExpect(jsonPath("$.title", is("Java")))
                .andExpect(jsonPath("$.author", is("aga")))
                .andExpect(jsonPath("$.id", is(1)));

    }

    @Test
    void getBookWithUnknownIdShouldReturn404() throws Exception {
        when(bookService.getBookById(42L)).thenThrow(new BookNotFoundException("not found book by id"));

        this.mockMvc
                .perform(get("/api/books/42"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBookWithKnownIShouldUpdateTheBook() throws Exception {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Duke");
        bookRequest.setIsbn("1337");
        bookRequest.setTitle("Java 12");

        when(bookService.updateBook(eq(1L), bookRequestArgumentCaptor.capture()))
                .thenReturn(createBook(1L, "Java 12", "Duke", "1337"));

        this.mockMvc
                .perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.title", is("Java 12")))
                .andExpect(jsonPath("$.author", is("Duke")))
                .andExpect(jsonPath("$.isbn", is("1337")))
                .andExpect(jsonPath("$.id", is(1)));

        assertThat(bookRequestArgumentCaptor.getValue().getAuthor(), is("Duke"));
        assertThat(bookRequestArgumentCaptor.getValue().getIsbn(), is("1337"));
        assertThat(bookRequestArgumentCaptor.getValue().getTitle(), is("Java 12"));
    }

    @Test
    void shouldReturn404WhenUnknownIDUpdateBook() throws Exception {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Duke");
        bookRequest.setIsbn("1337");
        bookRequest.setTitle("Java 12");

        when(bookService.updateBook(eq(42L), bookRequestArgumentCaptor.capture()))
                .thenThrow(new BookNotFoundException("not found book by id"));

        this.mockMvc
                .perform(put("/api/books/42")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                        .andExpect(status().isNotFound());

    }

    private Book createBook(long id, String title, String author, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setId(id);
        return book;

    }


}