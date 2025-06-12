package com.example.blogom.repository;

import com.example.blogom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingIgnoreCase(String keyword);
    List<Post> findAllByOrderByCreatedAtDesc();
    List<Post> findAllByOrderByCreatedAtAsc();
    List<Post> findAllByOrderByTitleAsc();
    List<Post> findAllByOrderByTitleDesc();

}
