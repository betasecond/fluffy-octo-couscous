package edu.jimei.praesidium.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Represents a single content item within a report section.
 * This can be a heading, paragraph, list, or a demo component placeholder.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportContentItem {

    private String type;
    private Integer level;
    private String text;
    private List<String> items;
    private Boolean ordered;
    private String demoComponent;
} 