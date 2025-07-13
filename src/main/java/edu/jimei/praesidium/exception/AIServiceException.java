package edu.jimei.praesidium.exception;

/**
 * Custom exception for errors occurring during interaction with an AI service.
 */
public class AIServiceException extends RuntimeException {

    public AIServiceException(String message) {
        super(message);
    }

    public AIServiceException(String message, Throwable cause) {
        super(message, cause);
    }
} 