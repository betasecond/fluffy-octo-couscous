package edu.jimei.praesidium.dto;

import lombok.Data;

import java.util.List;

@Data
public class MetadataDTO {
    private List<String> tags;
    private List<String> keywords;
    private String expirationDate;
} 