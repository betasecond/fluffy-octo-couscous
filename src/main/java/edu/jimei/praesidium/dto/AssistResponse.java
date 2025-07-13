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

    /**
     * A list of suggestions for the agent, such as recommended responses or actions.
     */
    private List<Suggestion> suggestions;
    /**
     * The result of the compliance analysis on the conversation.
     */
    private ComplianceAnalysis complianceAnalysis;
    /**
     * The result of the information completeness analysis.
     */
    private InformationCompleteness informationCompleteness;
    /**
     * The result of the sentiment analysis of the user's messages.
     */
    private SentimentAnalysis sentimentAnalysis;

    /**
     * Represents a single suggestion for the agent.
     */
    @Value
    @Builder
    public static class Suggestion implements Serializable {
        /** The type of the suggestion. */
        SuggestionType type;
        /** The content of the suggestion. */
        String content;
    }

    /**
     * Enum representing the different types of suggestions.
     */
    public enum SuggestionType {
        /** A suggestion related to compliance issues. */
        COMPLIANCE, 
        /** A suggestion to complete missing information. */
        INFORMATION, 
        /** A suggestion based on user sentiment. */
        SENTIMENT, 
        /** A suggestion from the knowledge base. */
        KNOWLEDGE
    }

    /**
     * Contains the result of compliance analysis.
     */
    @Value
    @Builder
    public static class ComplianceAnalysis implements Serializable {
        /** Whether any compliance issues were detected. */
        @com.fasterxml.jackson.annotation.JsonProperty("hasIssues")
        boolean hasIssues;
        /** A list of identified compliance issues. */
        List<String> issues;
    }

    /**
     * Contains the result of information completeness analysis.
     */
    @Value
    @Builder
    public static class InformationCompleteness implements Serializable {
        /** Whether the provided information is considered complete. */
        @com.fasterxml.jackson.annotation.JsonProperty("isComplete")
        boolean isComplete;
        /** A list of required information that is still missing. */
        List<String> missingInfo;
    }

    /**
     * Contains the result of sentiment analysis.
     */
    @Value
    @Builder
    public static class SentimentAnalysis implements Serializable {
        /** The overall sentiment type. */
        SentimentType type;
        /** The sentiment score, typically from -1.0 (very negative) to 1.0 (very positive). */
        double score;
    }

    /**
     * Enum representing the different types of sentiment.
     */
    public enum SentimentType {
        /** Positive sentiment. */
        POSITIVE, 
        /** Negative sentiment. */
        NEGATIVE, 
        /** Neutral sentiment. */
        NEUTRAL
    }
} 