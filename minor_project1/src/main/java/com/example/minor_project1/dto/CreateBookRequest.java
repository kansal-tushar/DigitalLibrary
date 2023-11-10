package com.example.minor_project1.dto;

import com.example.minor_project1.model.Author;
import com.example.minor_project1.model.Book;
import com.example.minor_project1.model.enums.Genre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;

    @NotNull
    private Genre genre;

    private Integer pages;

    @NotBlank
    private String authorName;

    private String authorCountry;

    @NotBlank
    private String authorEmail;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .pages(this.pages)
                .genre(this.genre)
                .my_author(
                        Author.builder()
                                .name(this.authorName)
                                .country(this.authorCountry)
                                .email(this.authorEmail)
                                .build()
                )
                .build();
    }
}
