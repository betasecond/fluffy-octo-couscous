package edu.jimei.praesidium.dto;

import lombok.Data;

import java.util.List;

/**
 * Represents a full section of a report, including its title and content.
 */
@Data
public class ReportSection {

    private String id;
    private String title;
    private String mainTitle;
    private List<ReportContentItem> content;
} 