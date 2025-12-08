package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.PostDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.entity.User;
import com.gildin.blog_service.exceptions.ErrorMessage;
import com.gildin.blog_service.repository.CommentRepository;
import com.gildin.blog_service.repository.UserRepository;
import com.gildin.blog_service.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Object> createPost(@RequestBody PostDTO postDTO) {
        try {
            Post createdPost = postService.createPost(convertToEntity(postDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdPost)); //201
        } catch (DataIntegrityViolationException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            System.out.println("=============================" + errorMessage + "=========================");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); //400
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage); //500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(this::convertToDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post updatedPost = postService.updatePost(id, post);
            return ResponseEntity.ok(updatedPost);
        } catch (DataIntegrityViolationException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (RuntimeException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    private Post convertToEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        if (postDTO.getLikedUsersIds() != null) {
            List<User> likedUsers = postDTO.getLikedUsersIds().stream()
                    .map(id -> userRepository.findById(id).orElseThrow(() -> new RuntimeException("post ne naiden")))
                    .collect(Collectors.toList());
            post.setLikedUsers(likedUsers);
        }
        post.setAuthorUser(userRepository.findById(postDTO.getAuthorUser()).orElseThrow(() -> new RuntimeException("avtor ne naiden")));
        post.setContentText(postDTO.getContentText());
        if (postDTO.getLikedUsersIds() != null) {
            List<Comment> comments = postDTO.getLikedUsersIds().stream()
                    .map(id -> commentRepository.findById(id).orElseThrow(() -> new RuntimeException("post ne naiden")))
                    .collect(Collectors.toList());
            post.setComments(comments);
        }
        return post;
    }

    private PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        if (post.getLikedUsers() != null) {
            postDTO.setLikedUsersIds(post.getLikedUsers().stream().map(User::getId).collect(Collectors.toList()));
        }
        postDTO.setAuthorUser(post.getAuthorUser().getId());
        postDTO.setContentText(post.getContentText());
        if (post.getComments() != null) {
            postDTO.setcommentsIds(post.getComments().stream().map(Comment::getId).collect(Collectors.toList()));
        }
        return postDTO;
    }
}
