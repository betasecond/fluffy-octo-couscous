package edu.jimei.praesidium.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for the response of an agent assistance request.
 * Contains various analyses and suggestions based on the conversation context.
 *
 * @author Advisor
 */
@Data
@Builder
public class AssistResponse implements Serializable {

    private List<Suggestion> suggestions;
    private ComplianceAnalysis complianceAnalysis;
    private InformationCompleteness informationCompleteness;
    private SentimentAnalysis sentimentAnalysis;

    /**
     * Represents a single suggestion for the agent.
     */
    @Value
    @Builder
    public static class Suggestion implements Serializable {
        SuggestionType type;
        String content;
    }

    public enum SuggestionType {
        COMPLIANCE, INFORMATION, SENTIMENT, KNOWLEDGE
    }

    /**
     * Contains the result of compliance analysis.
     */
    @Value
    @Builder
    public static class ComplianceAnalysis implements Serializable {
        boolean hasIssues;
        List<String> issues;
    }

    /**
     * Contains the result of information completeness analysis.
     */
    @Value
    @Builder
    public static class InformationCompleteness implements Serializable {
        boolean isComplete;
        List<String> missingInfo;
    }

    /**
     * Contains the result of sentiment analysis.
     */
    @Value
    @Builder
    public static class SentimentAnalysis implements Serializable {
        SentimentType type;
        double score;
    }

    public enum SentimentType {
        POSITIVE, NEGATIVE, NEUTRAL
    }
} 