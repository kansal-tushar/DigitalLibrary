package com.example.minor_project1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false)
    private String email;

    private String name;

    private String country;

    @CreationTimestamp
    private Date addedOn;

    @OneToMany(mappedBy = "my_author")//No need to create a new colum for bookList in the author table,just create a book reference
    @JsonIgnoreProperties({"my_author"})
    private List<Book> bookList;

    //GET author details given an author id
    //select * from author where id=? =>id,name,country,addedOn
    //select * from book where author.id=? =>books

}
