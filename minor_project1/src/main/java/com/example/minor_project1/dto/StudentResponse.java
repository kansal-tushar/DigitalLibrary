package com.example.minor_project1.dto;

import com.example.minor_project1.model.Student;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse implements Serializable {

    private int id;

    private String name;

    private String contact;

    private Date createdOn;

    private Date updatedOn;

    private Date validity;

    public StudentResponse(Student student){
        this.id= student.getId();
        this.name=student.getName();
        this.contact=student.getContact();
        this.createdOn=student.getCreatedOn();
        this.updatedOn=student.getUpdatedOn();
        this.validity=student.getValidity();
    }

}
