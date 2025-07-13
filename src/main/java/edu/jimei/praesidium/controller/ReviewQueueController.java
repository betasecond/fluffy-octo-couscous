package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.ApiResponse;
import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import edu.jimei.praesidium.service.ReviewQueueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for managing the knowledge base review queue.
 */
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewQueueController {

    private final ReviewQueueService reviewQueueService;

    /**
     * Retrieves a paginated list of review items based on their status.
     *
     * @param status   The status to filter by (e.g., PENDING, APPROVED, REJECTED). Defaults to PENDING.
     * @param pageable Pagination information.
     * @return A response entity containing a page of review items.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReviewItemDto>>> getReviewItems(
            @RequestParam(required = false, defaultValue = "PENDING") ReviewItemStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ReviewItemDto> page = reviewQueueService.getReviewItemsByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    /**
     * Retrieves a single review item by its ID.
     *
     * @param id The UUID of the review item.
     * @return A response entity containing the review item DTO.
     * @throws ResourceNotFoundException if the item with the given ID is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewItemDto>> getReviewItemById(@PathVariable UUID id) {
        return reviewQueueService.getReviewItemById(id)
                .map(dto -> ResponseEntity.ok(ApiResponse.success(dto)))
                .orElseThrow(() -> new ResourceNotFoundException("ReviewItem not found with id: " + id));
    }
    
    /**
     * Submits a review decision (approve or reject) for a review item.
     *
     * @param id      The UUID of the review item to be reviewed.
     * @param request The review decision, containing the new status and optional comments.
     * @return A response entity containing the updated review item DTO.
     */
    @PostMapping("/{id}/decision")
    public ResponseEntity<ApiResponse<ReviewItemDto>> makeReviewDecision(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewDecisionRequest request) {
        ReviewItemDto updatedItem = reviewQueueService.processReviewDecision(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedItem));
    }
} 