package com.example.minor_project1.service;

import com.example.minor_project1.dto.CreateStudentRequest;
import com.example.minor_project1.dto.StudentResponse;
import com.example.minor_project1.dto.UpdateStudentRequest;
import com.example.minor_project1.model.SecuredUser;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.repository.StudentCacheRepository;
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

    @Autowired
    StudentCacheRepository studentCacheRepository;

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
        long start = System.currentTimeMillis();
        Student student = studentRepository.findById(studentId).orElse(null);
        long end = System.currentTimeMillis();
        System.out.println("Inside get function: Time taken to retreive the data-"+(end-start));
        return student;
    }

    public StudentResponse getUsingCache(int studentId){
        long start = System.currentTimeMillis();
        StudentResponse studentResponse=studentCacheRepository.get(studentId);
        if(studentResponse==null){
            Student student = studentRepository.findById(studentId).orElse(null);
            studentResponse = new StudentResponse(student);
            studentCacheRepository.set(studentResponse);
            long end = System.currentTimeMillis();
            System.out.println("Inside get function: Time taken to retreive the data-"+(end-start));
        }else{
            long end = System.currentTimeMillis();
            System.out.println("Inside get function: Time taken to retreive the data-"+(end-start));
        }
        return studentResponse;
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
