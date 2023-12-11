package com.example.minor_project1.service;

import com.example.minor_project1.dto.CreateStudentRequest;
import com.example.minor_project1.dto.UpdateStudentRequest;
import com.example.minor_project1.model.SecuredUser;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Value("${student.authorities}")
    String authorities;

    public Student create(CreateStudentRequest createStudentRequest){
        Student student=createStudentRequest.to();
        SecuredUser securedUser=student.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));
        securedUser.setAuthorities(authorities);

        //create a user
        securedUser=userService.save(securedUser);
        //create a student
        student.setSecuredUser(securedUser);

        return studentRepository.save(student);
    }

    public Student get(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public Student delete(int studentId) {
        Student student=this.get(studentId);
        studentRepository.deleteById(studentId);
        return student;
    }

    public Student update(int studentId, UpdateStudentRequest updateStudentRequest) {
        //TO BE IMPLEMENTED
        return null;
    }
}
