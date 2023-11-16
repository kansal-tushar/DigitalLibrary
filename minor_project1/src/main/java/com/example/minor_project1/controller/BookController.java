package com.example.minor_project1.controller;

import com.example.minor_project1.dto.BookResponse;
import com.example.minor_project1.dto.CreateBookRequest;
import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("")
    public Book createBook(@RequestBody @Valid CreateBookRequest createBookRequest){
        return bookService.create(createBookRequest);
    }

    @DeleteMapping("/{bookId}")
    public BookResponse deleteBook(@PathVariable("bookId") int bookId){
        try{
            return BookResponse.from(bookService.delete(bookId));
            //return bookService.delete(bookId);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /*@GetMapping("/{authorEmail}")
    public List<Book> getBooksByAuthor(@PathVariable("authorEmail") String authorEmail){
        //Will you create different APIs for every attribute?
    }*/

    @GetMapping("/all")
    public List<Book>getAllBooks(){
        return bookService.get();
    }

    @GetMapping("/search")
    public List<Book> getBooks(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {
        return bookService.search(searchBookRequest);
    }

}
