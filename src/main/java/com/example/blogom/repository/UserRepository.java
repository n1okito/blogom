package com.example.blogom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {}
