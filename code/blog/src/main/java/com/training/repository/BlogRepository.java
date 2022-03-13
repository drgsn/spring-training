package com.training.repository;

import com.training.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends PagingAndSortingRepository<BlogPost, Long> {

    Page<BlogPost> findAll(Pageable date);

    Page<BlogPost> findAllByTitle(String title, Pageable page);

    Optional<BlogPost> findFirstByTitle(String title);

    @Query(value = "SELECT p FROM  blog_posts p WHERE p.title = :title ORDER BY p.date DESC")
    Page<BlogPost> getBlogPostsByTitle(@Param("title") String title, Pageable page);

    @Query(value = "SELECT p FROM  blog_posts p WHERE p.title = ?1 ORDER BY p.date DESC")
    Page<BlogPost> getBlogPostsByTitle2(String title, Pageable page);

//    @Query("FROM blog_posts WHERE UPPER(title) LIKE %?#{[0].toUpperCase()}%")
//    Page<BlogPost> findByTitleContainingIgnoreCase(String title);

    @Query("UPDATE blog_posts SET title = :prefix || title")
    @Modifying
    void addPrefixToTitle(@Param("prefix") String prefix);


}
