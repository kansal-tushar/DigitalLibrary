package com.example.minor_project1.repository;

import com.example.minor_project1.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {


    //Select * from author where email=?
    Author findByEmail(String email);

}
