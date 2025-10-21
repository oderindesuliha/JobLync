package org.peejay.joblync.dtos.responses;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private String details;
    
    public ErrorResponse() {
    }
    
    public ErrorResponse(String message) {
        this.message = message;
    }
    
    public ErrorResponse(String message, String details) {
        this.message = message;
        this.details = details;
    }
}