package com.imobiliaria_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;
    private LocalDateTime timestamp;
    
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data, LocalDateTime.now());
    }
    
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null, LocalDateTime.now());
    }
    
    public static ApiResponse error(String message, Object data) {
        return new ApiResponse(false, message, data, LocalDateTime.now());
    }
}