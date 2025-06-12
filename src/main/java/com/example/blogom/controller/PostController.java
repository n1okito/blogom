package com.example.blogom.controller;

import com.example.blogom.model.Post;
import com.example.blogom.model.User;
import com.example.blogom.repository.UserRepository;
import com.example.blogom.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // 📄 Összes bejegyzés megjelenítése + rendezés
    @GetMapping
    public String listPosts(
            @RequestParam(value = "sort", required = false, defaultValue = "latest") String sort,
            Model model
    ) {
        model.addAttribute("posts", postService.findAllSorted(sort));
        model.addAttribute("selectedSort", sort);
        return "posts";
    }

    // 🔍 Keresés címre vagy tartalomra
    @GetMapping("/search")
    public String searchPosts(@RequestParam("keyword") String keyword, Model model) {
        List<Post> foundPosts = postService.searchPosts(keyword);
        model.addAttribute("posts", foundPosts);

        if (foundPosts.isEmpty()) {
            model.addAttribute("message", "❗ Ilyen bejegyzés nem található.");
        }

        return "posts";
    }

    // 📝 Új bejegyzés űrlap
    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post_form";
    }

    // 💾 Bejegyzés mentése
    @PostMapping
    public String savePost(@ModelAttribute Post post) {
        String rawName = post.getAuthorName();
        String name = (rawName == null || rawName.trim().isEmpty()) ? "Ismeretlen" : rawName.trim();

        User author = userRepository.findByUsername(name)
                .orElseGet(() -> userRepository.save(new User(name)));

        post.setAuthor(author);
        postService.save(post);
        return "redirect:/posts";
    }

    // ✏️ Szerkesztés űrlap
    @GetMapping("/edit/{id}")
    public String editPostForm(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getAuthor() != null) {
                post.setAuthorName(post.getAuthor().getUsername());
            }
            model.addAttribute("post", post);
            return "post_form";
        }
        return "redirect:/posts";
    }

    // ❌ Törlés
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }
}
