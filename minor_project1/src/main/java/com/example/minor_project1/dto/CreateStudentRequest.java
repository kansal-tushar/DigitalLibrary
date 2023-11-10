package com.example.minor_project1.dto;

import com.example.minor_project1.model.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String contact;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .validity(new Date(System.currentTimeMillis()+31536000000l))
                .build();
    }

}
