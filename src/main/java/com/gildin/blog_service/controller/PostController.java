package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.create.CreatePostDTO;
import com.gildin.blog_service.dto.response.PostDTO;
import com.gildin.blog_service.dto.update.UpdatePostDTO;
import com.gildin.blog_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/by-title/{title}")
    public ResponseEntity<PostDTO> getPostByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(postService.getPostByTitle(title));
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid CreatePostDTO createPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(createPostDTO));
    }

    @GetMapping("/{username}")
    public Page<PostDTO> getPostsByUsername(@PathVariable String username, Pageable pageable) {
        return postService.findPostsByUsername(username, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping
    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody @Valid UpdatePostDTO updatePostDTO) {
        return ResponseEntity.ok(postService.updatePost(id, updatePostDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
