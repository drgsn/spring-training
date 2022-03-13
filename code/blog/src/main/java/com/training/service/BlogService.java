package com.training.service;


import com.training.model.BlogPost;
import com.training.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Iterable<BlogPost> getBlogPosts() {
        return blogRepository.findAll();
    }

    public BlogPost addBlogPost(BlogPost blogPost) {
        if (blogPost.getDate() == null) {
            blogPost.setDate(LocalDate.now());
        }
        return blogRepository.save(blogPost);
    }

    public void delete(Long id) {
        blogRepository.deleteById(id);
    }

    public Optional<BlogPost> getBlogPosts(Long postId) {
        return blogRepository.findById(postId);
    }
    public Optional<BlogPost> getBlogPostByTitle(String title) {
        return blogRepository.getBlogPostsByTitle(title, PageRequest.of(0, 1)).get().findFirst();
    }

    public Page<BlogPost> getBlogPostSortedByDateDesc() {
        return blogRepository.findAll(PageRequest.of(0, 20, Sort.by(DESC, "date")));
    }
}
