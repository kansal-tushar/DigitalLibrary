package com.example.minor_project1.repository;

import com.example.minor_project1.model.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser,Integer> {

    SecuredUser findByUsername(String user);

}
