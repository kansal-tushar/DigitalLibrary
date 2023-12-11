package com.example.minor_project1;

import com.example.minor_project1.dto.CreateAdminRequest;
import com.example.minor_project1.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinorProject1Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MinorProject1Application.class, args);
	}

	@Autowired
	AdminService adminService;

	@Override
	public void run(String... args) throws Exception {
//		adminService.create(
//				CreateAdminRequest.builder()
//						.name("Tushar")
//						.username("tushar@gmail.com")
//						.password("tushar123")
//						.build()
//		);
	}
}
