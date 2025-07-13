package edu.jimei.praesidium.entity;

import edu.jimei.praesidium.enums.ReviewItemStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a knowledge base item that is pending review.
 * This entity is mapped to the "review_items" table in the database.
 */
@Data
@Entity
@Table(name = "review_items")
public class ReviewItem {

    /**
     * The unique identifier for the review item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String source;
    
    @Column(columnDefinition = "TEXT")
    private String originalQuery;

    @Column(columnDefinition = "TEXT")
    private String currentAnswer;

    @Column(columnDefinition = "TEXT")
    private String suggestedAnswer;
    
    private String standardQuestion;

    /**
     * The current status of the review item (e.g., PENDING, APPROVED, REJECTED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewItemStatus status;

    /**
     * Any comments or feedback provided by the reviewer.
     */
    @Column(columnDefinition = "TEXT")
    private String comments;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    /**
     * The timestamp when the review item was created.
     */
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the review item was last updated.
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
} 