package com.isaac.serviceprovider1.repository;

import com.isaac.serviceprovider1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("from User where password = :password")
    List<User> getUserSpecifyPassword(String password);
}
