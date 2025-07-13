package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.dto.ReviewStatsDTO;
import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.entity.ServiceQA;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import edu.jimei.praesidium.repository.ReviewItemRepository;
import edu.jimei.praesidium.repository.ServiceQARepository;
import edu.jimei.praesidium.repository.specifications.ReviewItemSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import edu.jimei.praesidium.dto.ReviewItemResponse;
import edu.jimei.praesidium.dto.BatchOperationRequest;

@Service
@RequiredArgsConstructor
public class ReviewQueueServiceImpl implements ReviewQueueService {

    private final ReviewItemRepository reviewItemRepository;
    private final ServiceQARepository serviceQARepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public Page<ReviewItemDto> getReviewItemsByStatus(ReviewItemStatus status, Pageable pageable) {
        Page<ReviewItem> reviewItemsPage = reviewItemRepository.findByStatus(status, pageable);
        return reviewItemsPage.map(item -> modelMapper.map(item, ReviewItemDto.class));
    }

    @Override
    public ReviewStatsDTO getReviewStats() {
        long pendingCount = reviewItemRepository.countByStatus(ReviewItemStatus.PENDING);
        long approvedCount = reviewItemRepository.countByStatus(ReviewItemStatus.APPROVED);
        long rejectedCount = reviewItemRepository.countByStatus(ReviewItemStatus.REJECTED);
        long needsInfoCount = reviewItemRepository.countByStatus(ReviewItemStatus.NEEDS_INFO);
        long totalItems = reviewItemRepository.count();

        Map<String, Long> bySource = reviewItemRepository.countBySource().stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));

        return new ReviewStatsDTO(
                totalItems,
                pendingCount,
                approvedCount,
                rejectedCount,
                needsInfoCount,
                bySource
        );
    }

    @Override
    public Page<ReviewItemDto> findReviewItems(ReviewItemStatus status, String source, Pageable pageable) {
        Specification<ReviewItem> spec = ReviewItemSpecification.hasStatus(status)
                .and(ReviewItemSpecification.hasSource(source));

        return reviewItemRepository.findAll(spec, pageable)
                .map(item -> modelMapper.map(item, ReviewItemDto.class));
    }

    @Override
    public ReviewItemResponse getReviewItemDetailsById(UUID id) {
        ReviewItem reviewItem = reviewItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review item not found with id: " + id));

        ReviewItemResponse response = modelMapper.map(reviewItem, ReviewItemResponse.class);

        // Placeholder for future implementation
        response.setRelatedKnowledgeItems(Collections.emptyList());
        response.setReviewHistory(Collections.emptyList());

        return response;
    }

    @Override
    @Transactional
    public ReviewItemDto processReviewDecision(ReviewDecisionRequest request) {
        UUID id = UUID.fromString(request.getItemId());
        ReviewItem reviewItem = reviewItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReviewItem not found with id: " + id));

        switch (request.getDecision().toLowerCase()) {
            case "approve":
                reviewItem.setStatus(ReviewItemStatus.APPROVED);
                if (request.getStandardQuestion() != null && request.getSuggestedAnswer() != null) {
                    ServiceQA newKnowledge = new ServiceQA();
                    newKnowledge.setQuestion(request.getStandardQuestion());
                    newKnowledge.setAnswer(request.getSuggestedAnswer());
                    // Copy metadata if needed
                    serviceQARepository.save(newKnowledge);
                }
                break;
            case "reject":
                reviewItem.setStatus(ReviewItemStatus.REJECTED);
                break;
            case "needsinfo":
                reviewItem.setStatus(ReviewItemStatus.NEEDS_INFO);
                break;
            default:
                throw new IllegalArgumentException("Invalid decision: " + request.getDecision());
        }

        reviewItem.setComments(request.getComment());
        ReviewItem updatedReviewItem = reviewItemRepository.save(reviewItem);
        return modelMapper.map(updatedReviewItem, ReviewItemDto.class);
    }

    @Override
    @Transactional
    public int batchOperation(BatchOperationRequest request) {
        List<UUID> uuids = request.getItemIds().stream().map(UUID::fromString).collect(Collectors.toList());
        List<ReviewItem> itemsToUpdate = reviewItemRepository.findAllById(uuids);

        ReviewItemStatus statusToSet;
        switch (request.getOperation().toLowerCase()) {
            case "approve":
                statusToSet = ReviewItemStatus.APPROVED;
                break;
            case "reject":
                statusToSet = ReviewItemStatus.REJECTED;
                break;
            case "markneedsinfo":
                statusToSet = ReviewItemStatus.NEEDS_INFO;
                break;
            default:
                throw new IllegalArgumentException("Invalid batch operation: " + request.getOperation());
        }

        for (ReviewItem item : itemsToUpdate) {
            item.setStatus(statusToSet);
            item.setComments(request.getComment());
        }

        reviewItemRepository.saveAll(itemsToUpdate);
        return itemsToUpdate.size();
    }

    @Override
    public List<String> getAvailableSources() {
        return reviewItemRepository.findDistinctSources();
    }

    @Override
    public List<String> getAvailableTags() {
        return reviewItemRepository.findAll().stream()
                .map(ReviewItem::getMetadata)
                .filter(java.util.Objects::nonNull)
                .map(metadata -> (List<String>) metadata.get("tags"))
                .filter(java.util.Objects::nonNull)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
