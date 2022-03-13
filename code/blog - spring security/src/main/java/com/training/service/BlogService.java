package com.training.service;


import com.training.model.BlogPost;
import com.training.repository.BlogRepository;
import org.hibernate.Criteria;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class BlogService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private BlogRepository blogRepository;

    public Iterable<BlogPost> getBlogPostById() {
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

    public Optional<BlogPost> getBlogPostById(Long postId) {
        return blogRepository.findById(postId);
    }
    public Optional<BlogPost> getBlogPostByTitle(String title) {
        Page<BlogPost> blogPosts = blogRepository.getBlogPostsByTitle(title, PageRequest.of(0, 3));
        return blogPosts.get().findFirst();
    }

    public Page<BlogPost> getBlogPostSortedByDateDesc() {
        return blogRepository.findAll(PageRequest.of(0, 20, Sort.by(DESC, "date")));
    }
}
