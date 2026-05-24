package com.gildin.blog_service.service;

import com.gildin.blog_service.dto.create.CreatePostDTO;
import com.gildin.blog_service.dto.response.PostDTO;
import com.gildin.blog_service.dto.update.UpdatePostDTO;
import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.exceptions.ResourceNotFoundException;
import com.gildin.blog_service.mapper.PostMapper;
import com.gildin.blog_service.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public Page<PostDTO> findPostsByUsername(String username, Pageable pageable) {
        return postRepository.findPostsByUsername(username, pageable).map(postMapper::toDTO);
    }

    public PostDTO updatePost(Long id, UpdatePostDTO updatePostDTO) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("пост с указанным id не найден: " + id));
        postMapper.updateFromUpdateDto(updatePostDTO, post);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    public Page<PostDTO> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(postMapper::toDTO);
    }
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("пост не найден: " + id));
        return postMapper.toDTO(post);
    }
    public PostDTO createPost(CreatePostDTO createPostDTO) {
        Post post = postMapper.toEntity(createPostDTO);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    public PostDTO getPostByTitle(String title) {
        return postMapper.toDTO(postRepository.findByTitle(title));
    }
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("post not found: " + id));
        postRepository.delete(post);
    }
}
