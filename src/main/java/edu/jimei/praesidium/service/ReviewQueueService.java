package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.BatchOperationRequest;
import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.dto.ReviewStatsDTO;
import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;
import edu.jimei.praesidium.dto.ReviewItemResponse;
import java.util.List;

/**
 * Service interface for managing the knowledge base review queue.
 */
public interface ReviewQueueService {

    ReviewStatsDTO getReviewStats();

    Page<ReviewItemDto> findReviewItems(ReviewItemStatus status, String source, Pageable pageable);

    ReviewItemResponse getReviewItemDetailsById(UUID id);

    /**
     * Processes a review decision for a specific review item.
     *
     * @param request The review decision request, containing the new status and comments.
     * @return The updated review item DTO.
     * @throws ResourceNotFoundException if no review item is found with the given ID.
     * @throws IllegalArgumentException if the review decision is invalid (e.g., changing status to PENDING).
     */
    ReviewItemDto processReviewDecision(ReviewDecisionRequest request);

    int batchOperation(BatchOperationRequest request);

    List<String> getAvailableSources();

    List<String> getAvailableTags();
} 