package edu.jimei.praesidium.entity;

import edu.jimei.praesidium.enums.ReviewItemStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    /**
     * The ID of the knowledge base article or entry associated with this review item.
     */
    @Column(nullable = false)
    private String knowledgeId;

    /**
     * The actual content that needs to be reviewed.
     */
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    /**
     * The current status of the review item (e.g., PENDING, APPROVED, REJECTED).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewItemStatus status;

    /**
     * The ID of the user who performed the review.
     */
    private String reviewerId;

    /**
     * Any comments or feedback provided by the reviewer.
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String comments;

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