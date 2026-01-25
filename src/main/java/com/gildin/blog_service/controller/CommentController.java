package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.CommentDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.exceptions.ErrorMessage;
import com.gildin.blog_service.repository.CommentRepository;
import com.gildin.blog_service.repository.PostRepository;
import com.gildin.blog_service.repository.UserRepository;
import com.gildin.blog_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO commentDTO) {
        try {
            Comment createdComment = commentService.createComment(convertToEntity(commentDTO));
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdComment)); //201
        } catch (DataIntegrityViolationException e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); //400
        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage); //500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(this::convertToDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            Comment updatedcomment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedcomment);
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
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setAuthorUser(userRepository.findById(commentDTO.getAuthorUserId()).orElseThrow(() -> new RuntimeException("avtor ne naiden")));
        comment.setCommentedPost(postRepository.findById(commentDTO.getCommentedPostId()).orElseThrow(() -> new RuntimeException("post ne nashol")));
        comment.setContentText(commentDTO.getContentText());
        return comment;
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setAuthorUserId(comment.getAuthorUser().getId());
        commentDTO.setCommentedPostId(comment.getCommentedPost().getId());
        commentDTO.setContentText(comment.getContentText());
        return commentDTO;
    }
}
