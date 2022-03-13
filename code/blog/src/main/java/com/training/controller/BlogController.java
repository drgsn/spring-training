package com.training.controller;

import com.training.model.BlogPost;
import com.training.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/")
    private String index(Model model) {
        model.addAttribute("posts", blogService.getBlogPostSortedByDateDesc());
        return "home";
    }

    // http://localhost:8080/path-url/path-url-2?var=1&var2
    @GetMapping("/post/{id}")
    private String getBlogPost(@PathVariable("id") Long postId, Model model) {
        blogService.getBlogPosts(postId).ifPresent(post -> model.addAttribute("post", post));
        return "blog-post";
    }

    @GetMapping("/post")
    private String getBlogPost(@RequestParam("title") String title, Model model) {
        model.addAttribute("post", blogService.getBlogPostByTitle(title)
                .orElseThrow(EntityNotFoundException::new));
        return "blog-post";
    }

    @PostMapping("/blog-post")
    public String addBlogPost(@ModelAttribute @Valid BlogPost blogPost, Errors errors) {

        if (errors != null && errors.getErrorCount() > 0) {
            return "create_post_page";
        }

        blogService.addBlogPost(blogPost);
        return "home";
    }
    // blogPost

    @GetMapping("/create-post")
    public String createPost(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "create_post_page";
    }

    @RequestMapping(value = "/posts", method = GET)
    @ResponseBody
    public Iterable<BlogPost> blogPosts() {
        return blogService.getBlogPosts();
    }

    @PostMapping("/posts")
    @ResponseBody
    public BlogPost createPost(@RequestBody @Valid BlogPost blogPost) {
        return blogService.addBlogPost(blogPost);
    }

    @DeleteMapping("/posts")
    @ResponseBody
    public void deletePost(Long id) {
        blogService.delete(id);
    }


}
