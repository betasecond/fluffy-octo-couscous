package edu.jimei.praesidium.dto;

import lombok.Data;
import java.util.List;

/**
 * Data Transfer Object for ServiceQA.
 */
@Data
public class ServiceQADTO {
    private Long id;
    private String question;
    private String answer;
    private List<String> keywords;
} 