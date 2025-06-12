package com.example.blogom.service;

import com.example.blogom.model.Post;
import com.example.blogom.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 🔎 Keresés cím alapján
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCase(keyword);
    }

    // 📄 Rendezett lekérdezés
    public List<Post> findAllSorted(String sort) {
        return switch (sort) {
            case "oldest" -> postRepository.findAllByOrderByCreatedAtAsc();
            case "titleAsc" -> postRepository.findAllByOrderByTitleAsc();
            case "titleDesc" -> postRepository.findAllByOrderByTitleDesc();
            default -> postRepository.findAllByOrderByCreatedAtDesc(); // legújabb elöl
        };
    }

    // 📄 Minden bejegyzés (alaprendezés nélkül)
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 📄 ID alapján lekérés
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    // 💾 Mentés új bejegyzésként
    public Post save(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // ❌ Törlés ID alapján
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
