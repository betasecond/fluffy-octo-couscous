package edu.jimei.praesidium.dto;

import edu.jimei.praesidium.enums.ReviewItemStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewItemDto {
    private String id;
    private String source;
    private String originalQuery;
    private String currentAnswer;
    private String suggestedAnswer;
    private String timestamp;
    private ReviewItemStatus status;
    private String standardQuestion;
    private MetadataDTO metadata;
} 