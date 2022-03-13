package com.training.controller;

import com.training.exception.ServerError;
import com.training.exception.TestException;
import com.training.model.BlogPost;
import com.training.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller("/todos")
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

    @GetMapping("/test")
    private void indexTest() throws ServerError {
        throw new ServerError();
    }

    @GetMapping("/test2")
    private void testException() throws TestException {
        throw new TestException();
    }

    // http://localhost:8080/path-url/path-url-2?var=1&var2
    @GetMapping("/post/{id}")
    private String getBlogPost(@PathVariable("id") Long postId, Model model) {
        blogService.getBlogPostById(postId).ifPresent(post -> model.addAttribute("post", post));
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
        return blogService.getBlogPostById();
    }

    @PostMapping("/posts")
    @ResponseBody
    public BlogPost createPost(@RequestBody @Valid BlogPost blogPost) {
        return blogService.addBlogPost(blogPost);
    }

    @Operation(summary = "delete a post by id", description = "delete a post by id")
    @DeleteMapping("/posts")
    @ResponseBody
    public void deletePost(Long id) {
        blogService.delete(id);
    }
    
}
