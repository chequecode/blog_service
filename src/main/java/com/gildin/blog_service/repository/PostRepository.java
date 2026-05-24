package com.gildin.blog_service.repository;

import com.gildin.blog_service.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    @Query("SELECT p FROM Post p JOIN User u WHERE p.authorUser.username = :username")
    Page<Post> findPostsByUsername(String username, Pageable pageable);
}
