package com.example.minor_project1.repository;

import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findByName(String name);

    List<Book> findByGenre(Genre genre);
}
