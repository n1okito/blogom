package com.example.blogom.controller;

import com.example.blogom.model.Post;
import com.example.blogom.model.User;
import com.example.blogom.repository.UserRepository;
import com.example.blogom.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    public PostController(PostService postService, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository = userRepository;
    }

    // ✅ Listaoldal: GET /posts
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    // ✅ Új post űrlap: GET /posts/new
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post_form";
    }

    // ✅ Mentés: POST /posts
    @PostMapping
    public String savePost(@ModelAttribute Post post) {
        String inputName = post.getAuthorName();
        String name = (inputName == null || inputName.trim().isEmpty()) ? "Ismeretlen" : inputName.trim();

        Optional<User> existingUser = userRepository.findByUsername(name);
        User author = existingUser.orElseGet(() -> userRepository.save(new User(name)));

        post.setAuthor(author);
        postService.save(post);

        return "redirect:/posts";
    }

    // ✅ Szerkesztés: GET /posts/edit/{id}
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            if (p.getAuthor() != null) {
                p.setAuthorName(p.getAuthor().getUsername());
            }
            model.addAttribute("post", p);
            return "post_form";
        }
        return "redirect:/posts";
    }

    // ✅ Törlés: GET /posts/delete/{id}
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
