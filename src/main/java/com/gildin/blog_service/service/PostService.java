package com.gildin.blog_service.service;

import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("post not found"));
        post.setLikedUsers(postDetails.getLikedUsers());
        post.setAuthorUser(postDetails.getAuthorUser());
        post.setContentText(postDetails.getContentText());
        post.setComments(postDetails.getComments());

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }
    public Post createPost(Post post) {
        return postRepository.save(post);
    }
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("post not found"));
        postRepository.delete(post);
    }
}
