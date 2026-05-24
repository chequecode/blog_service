package com.gildin.blog_service.service;

import com.gildin.blog_service.dto.create.CreateCommentDTO;
import com.gildin.blog_service.dto.response.CommentDTO;
import com.gildin.blog_service.dto.update.UpdateCommentDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.exceptions.ResourceNotFoundException;
import com.gildin.blog_service.mapper.CommentMapper;
import com.gildin.blog_service.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public CommentDTO updateComment(Long id, UpdateCommentDTO updateCommentDTO) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found: " + id));
        commentMapper.updateFromUpdateDto(updateCommentDTO, comment);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    public Page<CommentDTO> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable).map(commentMapper::toDTO);
    }
    public CommentDTO getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден " + id));
        return commentMapper.toDTO(comment);
    }
    public CommentDTO createComment(CreateCommentDTO createCommentDTO) {
        Comment comment = commentRepository.save(commentMapper.toEntity(createCommentDTO));
        return commentMapper.toDTO(comment);
    }
    public void deleteComment(Long id) {
        Comment Comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(Comment);
    }
}
