package com.gildin.blog_service.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtCore jwtCore;
    @Autowired
    private UserDetailsService userDetailsService;
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String[] openEndpoints = {
                "/auth/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        };
        for (String pattern : openEndpoints) {
            if (PATH_MATCHER.match(pattern, path)) {
                logger.debug("Skipping JWT filter for URI: {}", path);
                return true;
            }
        }
        return false;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");
        String jwtToken;
        String username;
        logger.debug("Processing request for URI: {}, Authorization header: {}", request.getRequestURI(), request.getHeader("Authorization"));

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.debug("No valid JWT token found for URI: {}", request.getRequestURI());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Authentication failed: No valid JWT token provided\"}");
                return;
            }

            jwtToken = authHeader.substring(7);
            username = jwtCore.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication failed: Invalid JWT token\"}");
            return;
        } catch (ExpiredJwtException e) {
            logger.warn("JWT token is expired: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication failed: JWT token is expired\"}");
            return;
        } catch (MalformedJwtException | SignatureException e) {
            logger.error("Malformed or invalid signature JWT token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Authentication failed: Malformed or invalid signature JWT token\"}");
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtCore.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Authenticated user: {} for URI: {}", username, request.getRequestURI());
            } else {
                logger.warn("Invalid JWT token for user: {}", username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Authentication failed: Invalid JWT token for user " + username + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);

    }
}
