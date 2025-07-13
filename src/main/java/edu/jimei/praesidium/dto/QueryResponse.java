package edu.jimei.praesidium.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for the response of a query.
 *
 * @author Advisor
 */
@Data
@Builder
public class QueryResponse implements Serializable {

    private String suggestedAnswer;
    private double confidence;
    private List<String> keywordAnalysis;
    private boolean needsHumanReview;

} 