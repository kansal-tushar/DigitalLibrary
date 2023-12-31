package com.example.minor_project1.service;

import com.example.minor_project1.dto.CreateBookRequest;
import com.example.minor_project1.dto.SearchBookRequest;
import com.example.minor_project1.model.Author;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.Transaction;
import com.example.minor_project1.model.enums.Genre;
import com.example.minor_project1.repository.AuthorRepository;
import com.example.minor_project1.repository.BookRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    //@Autowired
    //AuthorRepository authorRepository;

    @Autowired
    AuthorService authorService;

    //2 to 3 queries
    public Book create(CreateBookRequest createBookRequest){
        Book book=createBookRequest.to();
        //Author author=authorRepository.save(book.getMy_author());
        Author author=authorService.createOrGet(book.getMy_author());
        book.setMy_author(author);
        return bookRepository.save(book);
    }

    public Book delete(int bookId) {
        try{
            Book book=this.bookRepository.findById(bookId).orElse(null);
            List<Transaction> txnList = book.getTransactionList();
            bookRepository.deleteById(bookId);
            return book;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception {
//        boolean isValidRequest=searchBookRequest.validate();
//        if(!isValidRequest){
//            throw new Exception("Invalid Request");
//        }

        //String sql="select * from Book b where b.searchKey searchOperator searchValue";

        switch (searchBookRequest.getSearchKey()){
            case "name":
                if(searchBookRequest.isAvailable()){
                    return bookRepository.findByNameAndmy_studentIsNull(searchBookRequest.getSearchValue());
                }
                return bookRepository.findByName(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id":{
                Book book=bookRepository.findById(Integer.valueOf(searchBookRequest.getSearchValue())).orElse((null));
                return Arrays.asList(book);
            }
            default:
                throw new Exception("Invalid searchKey");
        }

        //return null;

    }

    public List<Book> get() {
        return bookRepository.findAll();
    }

    public void assignBookToStudent(Book book, Student student) {
        bookRepository.assignBookToStudent(book.getId(), student);
    }

    public void unassignBookFromStudent(Book book) {
        bookRepository.unassignBook(book.getId());
    }
}
