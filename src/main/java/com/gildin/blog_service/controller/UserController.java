package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.UserDTO;
import com.gildin.blog_service.entity.Comment;
import com.gildin.blog_service.entity.Post;
import com.gildin.blog_service.entity.User;
import com.gildin.blog_service.exceptions.ErrorMessage;
import com.gildin.blog_service.repository.CommentRepository;
import com.gildin.blog_service.repository.PostRepository;
import com.gildin.blog_service.repository.UserRepository;
import com.gildin.blog_service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO) {
        logger.info("POST /user - Creating new user with username: {}", userDTO.getUsername());
        logger.debug("User details: email={}, role={}", userDTO.getEmail(), userDTO.getRole());

        try {
            if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username already taken");
            }
            if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already taken");
            }

            User createdUser = userService.createUser(convertToEntity(userDTO));
            logger.info("User created successfully with ID: {}", createdUser.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(createdUser)); //201
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while creating user: {}", e.getMessage(), e);
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage); //400
        } catch (Exception e) {
            logger.error("Internal server error while creating user: {}", e.getMessage(), e);
            ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage); //500
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(this::convertToDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getLikedPostsIds() != null) {
            List<Post> likedPosts = userDTO.getLikedPostsIds().stream()
                    .map(id -> postRepository.findById(id).orElseThrow(() -> new RuntimeException("post ne naiden")))
                    .collect(Collectors.toList());
            user.setLikedPosts(likedPosts);
        }
        if (userDTO.getUserPostsIds() != null) {
            List<Post> userPosts = userDTO.getUserPostsIds().stream()
                    .map(id -> postRepository.findById(id).orElseThrow(() -> new RuntimeException("post ne naiden")))
                    .collect(Collectors.toList());
            user.setUserPosts(userPosts);
        }
        if (userDTO.getUserCommentsIds() != null) {
            List<Comment> comments = userDTO.getUserCommentsIds().stream()
                    .map(id -> commentRepository.findById(id).orElseThrow(() -> new RuntimeException("comment ne naiden")))
                    .collect(Collectors.toList());
            user.setUserComments(comments);
        }
        user.setRole(userDTO.getRole());
        return user;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setLikedPostsIds(user.getLikedPosts().stream().map(Post::getId).collect(Collectors.toList()));
        userDTO.setUserPostsIds(user.getUserPosts().stream().map(Post::getId).collect(Collectors.toList()));
        userDTO.setUserCommentsIds(user.getUserComments().stream().map(Comment::getId).collect(Collectors.toList()));
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
