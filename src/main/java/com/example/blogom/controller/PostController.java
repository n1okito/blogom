package com.example.blogom.controller;

import com.example.blogom.model.Post;
import com.example.blogom.model.User;
import com.example.blogom.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ➕ Gyökér ("/") átirányítás a /posts oldalra
    @GetMapping("/")
    public String redirectRoot() {
        return "redirect:/posts";
    }

    // Listázás
    @GetMapping
    public String listPosts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    // Új post űrlap
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post_form";
    }

    // Mentés
    @PostMapping
    public String savePost(@ModelAttribute Post post) {
        // Például legyen fix User, ha nincs login
        User dummyAuthor = new User("Anonymous");
        post.setAuthor(dummyAuthor);
        postService.save(post);
        return "redirect:/posts";
    }

    // Szerkesztés űrlap
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "post_form";
        }
        return "redirect:/posts";
    }

    // Törlés
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
