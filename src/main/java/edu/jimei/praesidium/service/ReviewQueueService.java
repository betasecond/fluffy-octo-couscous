package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for managing the knowledge base review queue.
 */
public interface ReviewQueueService {

    /**
     * Retrieves a paginated list of review items based on their status.
     *
     * @param status   The status to filter by (e.g., PENDING, APPROVED, REJECTED).
     * @param pageable Pagination information.
     * @return A page of review item DTOs.
     */
    Page<ReviewItemDto> getReviewItemsByStatus(ReviewItemStatus status, Pageable pageable);

    /**
     * Processes a review decision for a specific review item.
     *
     * @param id      The UUID of the review item to process.
     * @param request The review decision request, containing the new status and comments.
     * @return The updated review item DTO.
     * @throws ResourceNotFoundException if no review item is found with the given ID.
     * @throws IllegalArgumentException if the review decision is invalid (e.g., changing status to PENDING).
     */
    ReviewItemDto processReviewDecision(UUID id, ReviewDecisionRequest request);

    /**
     * Retrieves a single review item by its ID.
     *
     * @param id The UUID of the review item.
     * @return An Optional containing the review item DTO if found, otherwise empty.
     */
    Optional<ReviewItemDto> getReviewItemById(UUID id);
} 