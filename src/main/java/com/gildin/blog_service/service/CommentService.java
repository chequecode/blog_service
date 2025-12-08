package com.gildin.blog_service.service;

import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setCommentedPost(commentDetails.getCommentedPost());
        comment.setAuthorUser(commentDetails.getAuthorUser());
        comment.setContentText(commentDetails.getContentText());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
    public Comment createComment(Comment Comment) {
        return commentRepository.save(Comment);
    }
    public void deleteComment(Long id) {
        Comment Comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(Comment);
    }
}
