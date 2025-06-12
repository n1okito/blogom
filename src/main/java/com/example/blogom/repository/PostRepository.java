package com.example.blogom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.blogom.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {}