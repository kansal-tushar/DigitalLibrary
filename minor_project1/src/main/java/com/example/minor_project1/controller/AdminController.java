package com.example.minor_project1.controller;

import com.example.minor_project1.dto.CreateAdminRequest;
import com.example.minor_project1.model.Admin;
import com.example.minor_project1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/admin")
    public Admin createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest){
        return adminService.create(createAdminRequest);
    }

}
