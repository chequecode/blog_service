package com.gildin.blog_service.service;

import com.gildin.blog_service.dto.create.CreateUserDTO;
import com.gildin.blog_service.dto.response.UserDTO;
import com.gildin.blog_service.dto.update.UpdateUserDTO;
import com.gildin.blog_service.entity.User;
import com.gildin.blog_service.exceptions.ConflictException;
import com.gildin.blog_service.exceptions.ResourceNotFoundException;
import com.gildin.blog_service.mapper.UserMapper;
import com.gildin.blog_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        if (userRepository.existsByUsername(createUserDTO.getUsername())) {
            throw new ConflictException("пользователь с таким ником уже существует: " + createUserDTO.getUsername());
        } else if (userRepository.existsByEmail(createUserDTO.getEmail())) {
            throw new ConflictException("пользователь с такой почтой уже существует: " + createUserDTO.getEmail());
        }
        User savedUser = userRepository.save(userMapper.toEntity(createUserDTO));
        return userMapper.toDTO(savedUser);
    }

    public UserDTO updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("пользователь не найден: " + id));
        if (userRepository.existsByUsername(updateUserDTO.getUsername())) {
            throw new ConflictException("пользователь с таким ником уже существует, обновить нельзя: " + updateUserDTO.getUsername());
        } else if (userRepository.existsByEmail(updateUserDTO.getEmail())) {
            throw new ConflictException("пользователь с такой почтой уже существует, обновить нельзя: " + updateUserDTO.getEmail());
        }
        userMapper.updateFromUpdateDto(updateUserDTO, user);
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found by id: " + id));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("пользователь с таким ником не найден: " + username));
        return userMapper.toDTO(user);
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("пользователь с такой почтой не найден: " + email));
        return userMapper.toDTO(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user not found by id: " + id));
        userRepository.delete(user);
    }
}