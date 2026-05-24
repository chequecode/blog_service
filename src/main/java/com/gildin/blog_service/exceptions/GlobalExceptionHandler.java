package com.gildin.blog_service.exceptions;

import com.gildin.blog_service.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - пользователь не найден
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                "USER_NOT_FOUND",
                "Пользователь не найден",
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // 400 - ввод неверных данных
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                "BAD_REQUEST",
                "Введены неверные данные",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // 400 - ошибка валидации токена
    @ExceptionHandler(InvalidJwtTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtTokenException(InvalidJwtTokenException e, HttpServletRequest request) {
        ErrorResponse response = new ErrorResponse(
                "INVALID_JWT",
                "У пользователя неподхдоящий JWT",
                HttpStatus.FORBIDDEN.value(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    // 500 - непредвиденные ошибки
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse response = new ErrorResponse(
                "INTERNAL_ERROR",
                "Произошла внутренняя ошибка сервера",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
