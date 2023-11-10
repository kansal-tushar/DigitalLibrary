package com.example.minor_project1.service;

import com.example.minor_project1.dto.CreateStudentRequest;
import com.example.minor_project1.dto.UpdateStudentRequest;
import com.example.minor_project1.model.Student;
import com.example.minor_project1.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Student create(CreateStudentRequest createStudentRequest){
        Student student=createStudentRequest.to();
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
