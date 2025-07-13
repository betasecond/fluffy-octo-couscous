package edu.jimei.praesidium.controller;

import edu.jimei.praesidium.dto.ApiResponse;
import edu.jimei.praesidium.dto.BatchOperationRequest;
import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.dto.ReviewItemResponse;
import edu.jimei.praesidium.dto.ReviewStatsDTO;
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

import java.util.List;
import java.util.UUID;

/**
 * Controller for managing the knowledge base review queue.
 */
@RestController
@RequestMapping({
        "/api/v1/review-queue", "/api/v1/reviewQueue",
        "/api/review-queue", "/api/reviewQueue"
})
@RequiredArgsConstructor
public class ReviewQueueController {

    private final ReviewQueueService reviewQueueService;

    @GetMapping("/stats")
    public ApiResponse<ReviewStatsDTO> getReviewStats() {
        ReviewStatsDTO stats = reviewQueueService.getReviewStats();
        return ApiResponse.success(stats);
    }

    /**
     * Retrieves a paginated list of review items based on their status.
     *
     * @param status   The status to filter by (e.g., PENDING, APPROVED, REJECTED). Defaults to PENDING.
     * @param pageable Pagination information.
     * @return A response entity containing a page of review items.
     */
    @GetMapping({"/items", "/", ""})
    public ApiResponse<Page<ReviewItemDto>> getReviewItems(
            @RequestParam(required = false) ReviewItemStatus status,
            @RequestParam(required = false) String source,
            Pageable pageable) {
        return ApiResponse.success(reviewQueueService.findReviewItems(status, source, pageable));
    }

    @GetMapping("/items/{id}")
    public ApiResponse<ReviewItemResponse> getReviewItemDetails(@PathVariable UUID id) {
        return ApiResponse.success(reviewQueueService.getReviewItemDetailsById(id));
    }

    /**
     * Submits a review decision (approve or reject) for a review item.
     *
     * @param id      The UUID of the review item to be reviewed.
     * @param request The review decision, containing the new status and optional comments.
     * @return A response entity containing the updated review item DTO.
     */
    @PostMapping("/decision")
    public ApiResponse<ReviewItemDto> submitDecision(@RequestBody @Valid ReviewDecisionRequest request) {
        return ApiResponse.success(reviewQueueService.processReviewDecision(request));
    }

    @PostMapping("/batch-operation")
    public ApiResponse<Integer> batchOperation(@RequestBody @Valid BatchOperationRequest request) {
        int processedCount = reviewQueueService.batchOperation(request);
        return ApiResponse.success(processedCount);
    }

    @GetMapping("/sources")
    public ApiResponse<List<String>> getSources() {
        return ApiResponse.success(reviewQueueService.getAvailableSources());
    }

    @GetMapping("/tags")
    public ApiResponse<List<String>> getTags() {
        return ApiResponse.success(reviewQueueService.getAvailableTags());
    }
} 