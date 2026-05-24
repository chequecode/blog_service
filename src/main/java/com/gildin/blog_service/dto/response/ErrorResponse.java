package com.gildin.blog_service.dto.response;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String code;
    private String message;
    private int status;
    private LocalDateTime time;

    public ErrorResponse(String code, String message, int status, LocalDateTime time) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.time = time;
    }


}
