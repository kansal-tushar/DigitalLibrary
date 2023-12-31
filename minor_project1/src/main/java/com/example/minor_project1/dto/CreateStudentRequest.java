package com.example.minor_project1.dto;

import com.example.minor_project1.model.SecuredUser;
import com.example.minor_project1.model.Student;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .contact(this.contact)
                .securedUser(
                        SecuredUser.builder()
                                .password(password)
                                .username(username)
                                .build()
                )
                .validity(new Date(System.currentTimeMillis() + 31536000000l))
                .build();
    }

}
