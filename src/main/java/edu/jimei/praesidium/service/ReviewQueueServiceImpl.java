package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import edu.jimei.praesidium.repository.ReviewItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewQueueServiceImpl implements ReviewQueueService {

    private final ReviewItemRepository reviewItemRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewItemDto> getReviewItemsByStatus(ReviewItemStatus status, Pageable pageable) {
        Page<ReviewItem> reviewItemsPage = reviewItemRepository.findByStatus(status, pageable);
        return reviewItemsPage.map(this::convertToDto);
    }

    @Override
    @Transactional
    public ReviewItemDto processReviewDecision(UUID id, ReviewDecisionRequest request) {
        ReviewItem reviewItem = reviewItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewItem not found with id: " + id));

        if (request.getStatus() == ReviewItemStatus.PENDING) {
            throw new IllegalArgumentException("Cannot change status to PENDING.");
        }
        
        // A simple business rule: comments are required when rejecting.
        if (request.getStatus() == ReviewItemStatus.REJECTED && (request.getComments() == null || request.getComments().isBlank())) {
            throw new IllegalArgumentException("Comments are required when rejecting a review item.");
        }

        reviewItem.setStatus(request.getStatus());
        reviewItem.setComments(request.getComments());
        // In a real application, the reviewerId would be fetched from the security context
        // reviewItem.setReviewerId(SecurityContextHolder.getContext().getAuthentication().getName());

        ReviewItem updatedReviewItem = reviewItemRepository.save(reviewItem);
        return convertToDto(updatedReviewItem);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewItemDto> getReviewItemById(UUID id) {
        return reviewItemRepository.findById(id).map(this::convertToDto);
    }

    private ReviewItemDto convertToDto(ReviewItem reviewItem) {
        if (reviewItem == null) {
            return null;
        }
        ReviewItemDto dto = new ReviewItemDto();
        dto.setId(reviewItem.getId());
        dto.setKnowledgeId(reviewItem.getKnowledgeId());
        dto.setContent(reviewItem.getContent());
        dto.setStatus(reviewItem.getStatus());
        dto.setReviewerId(reviewItem.getReviewerId());
        dto.setComments(reviewItem.getComments());
        dto.setCreatedAt(reviewItem.getCreatedAt());
        dto.setUpdatedAt(reviewItem.getUpdatedAt());
        return dto;
    }
} 