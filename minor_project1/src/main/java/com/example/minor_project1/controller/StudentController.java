package com.example.minor_project1.controller;

import com.example.minor_project1.dto.CreateStudentRequest;
import com.example.minor_project1.dto.StudentResponse;
import com.example.minor_project1.dto.UpdateStudentRequest;
import com.example.minor_project1.model.SecuredUser;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.service.StudentService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("")
    public Student createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/{studentId}")//can be invoked by admin only
    public Student getStudentForAdmin(@PathVariable("studentId") int studentId){
        SecurityContext securityContext= SecurityContextHolder.getContext();
        Authentication authentication=securityContext.getAuthentication();
        SecuredUser securedUser=(SecuredUser) authentication.getPrincipal();
        return studentService.get(studentId);
    }

    @GetMapping("/details")        // this API return the student details who is calling this api
    public StudentResponse getStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return  studentService.getUsingCache(studentId);
    }

    @DeleteMapping("") // only accessible to student
    public Student deleteStudent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.delete(studentId);
    }

    @PutMapping("/{studentId}")
    public Student updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.update(studentId,updateStudentRequest);
    }

}
