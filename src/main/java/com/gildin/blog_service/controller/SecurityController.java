package com.gildin.blog_service.controller;

import com.gildin.blog_service.dto.request.SignInRequest;
import com.gildin.blog_service.dto.request.SignUpRequest;
import com.gildin.blog_service.enumTypes.RoleType;
import com.gildin.blog_service.entity.User;
import com.gildin.blog_service.repository.UserRepository;
import com.gildin.blog_service.security.JwtCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;

    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    public SecurityController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtCore jwtCore) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtCore = jwtCore;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.info("Signup request received for username: {}", signUpRequest.getUsername());
        try {
            if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
                logger.warn("Username already exists: {}", signUpRequest.getUsername());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already taken");
            }

            if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
                logger.warn("Email already exists: {}", signUpRequest.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already taken");
            }

            String hashedPassword = passwordEncoder.encode(signUpRequest.getPassword());
            User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(hashedPassword);
            user.setRole(RoleType.USER);
            userRepository.save(user);

            logger.info("User registered successfully: {}", signUpRequest.getUsername());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Signup error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        logger.info("Signin request received for username: {}", signInRequest.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequest.getUsername(),
                            signInRequest.getPassword()
                    )
            );

            String jwt = jwtCore.generateToken(authentication);
            logger.info("User signed in successfully: {}", signInRequest.getUsername());

            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            logger.warn("Invalid credentials for username: {}", signInRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            logger.error("Signin error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }
}
