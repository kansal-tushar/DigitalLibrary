package com.example.minor_project1.dto;

import com.example.minor_project1.model.Author;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.model.enums.Genre;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponse {

    private int id;

    private String name;

    private Genre genre;

    private Integer pages;

    private Author my_author;

    private Student my_student;

    private Date createdOn;

    private Date updatedOn;

    public static BookResponse from(Book b){
        return BookResponse.builder()
                .id(b.getId())
                .genre(b.getGenre())
                .createdOn(b.getCreatedOn())
                .updatedOn(b.getUpdatedOn())
                .pages(b.getPages())
                .my_author(b.getMy_author())
                .my_student(b.getMy_student())
                .name(b.getName())
                .build();
    }

}
