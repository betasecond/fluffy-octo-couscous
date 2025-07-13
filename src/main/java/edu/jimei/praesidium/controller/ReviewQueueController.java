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

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewQueueController {

    private final ReviewQueueService reviewQueueService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ReviewItemDto>>> getReviewItems(
            @RequestParam(required = false, defaultValue = "PENDING") ReviewItemStatus status,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ReviewItemDto> page = reviewQueueService.getReviewItemsByStatus(status, pageable);
        return ResponseEntity.ok(ApiResponse.success(page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewItemDto>> getReviewItemById(@PathVariable UUID id) {
        return reviewQueueService.getReviewItemById(id)
                .map(dto -> ResponseEntity.ok(ApiResponse.success(dto)))
                .orElseThrow(() -> new ResourceNotFoundException("ReviewItem not found with id: " + id));
    }
    
    @PostMapping("/{id}/decision")
    public ResponseEntity<ApiResponse<ReviewItemDto>> makeReviewDecision(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewDecisionRequest request) {
        ReviewItemDto updatedItem = reviewQueueService.processReviewDecision(id, request);
        return ResponseEntity.ok(ApiResponse.success(updatedItem));
    }
} 