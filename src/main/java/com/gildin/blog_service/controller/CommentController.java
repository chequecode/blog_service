package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.create.CreateCommentDTO;
import com.gildin.blog_service.dto.response.CommentDTO;
import com.gildin.blog_service.dto.update.UpdateCommentDTO;
import com.gildin.blog_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@RequestBody CreateCommentDTO createCommentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(createCommentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentById(id));
    }

    @GetMapping
    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentService.getAllComments(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody UpdateCommentDTO updateCommentDTO) {
        return ResponseEntity.ok(commentService.updateComment(id, updateCommentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
