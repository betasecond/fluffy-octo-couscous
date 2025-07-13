package edu.jimei.praesidium.dto;

import edu.jimei.praesidium.enums.ReviewItemStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReviewItemDto {
    private UUID id;
    private String knowledgeId;
    private String content;
    private ReviewItemStatus status;
    private String reviewerId;
    private String comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 