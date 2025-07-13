package edu.jimei.praesidium.service;

import edu.jimei.praesidium.dto.ReviewDecisionRequest;
import edu.jimei.praesidium.dto.ReviewItemDto;
import edu.jimei.praesidium.dto.ReviewStatsDTO;
import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import edu.jimei.praesidium.exception.ResourceNotFoundException;
import edu.jimei.praesidium.repository.ReviewItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ReviewQueueServiceImplTest {

    @Mock
    private ReviewItemRepository reviewItemRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReviewQueueServiceImpl reviewQueueService;

    private ReviewItem reviewItem;
    private UUID reviewItemId;

    @BeforeEach
    void setUp() {
        reviewItemId = UUID.randomUUID();
        reviewItem = new ReviewItem();
        reviewItem.setId(reviewItemId);
        reviewItem.setKnowledgeId("knowledge-123");
        reviewItem.setContent("This is the content to be reviewed.");
        reviewItem.setStatus(ReviewItemStatus.PENDING);
        reviewItem.setCreatedAt(LocalDateTime.now());
        reviewItem.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void getReviewItemById_whenItemExists_shouldReturnDto() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.of(reviewItem));

        Optional<ReviewItemDto> result = reviewQueueService.getReviewItemById(reviewItemId);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(reviewItemId);
    }

    @Test
    void getReviewItemById_whenItemNotExists_shouldReturnEmpty() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.empty());

        Optional<ReviewItemDto> result = reviewQueueService.getReviewItemById(reviewItemId);

        assertThat(result).isNotPresent();
    }

    @Test
    void getReviewItemsByStatus_shouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewItem> page = new PageImpl<>(Collections.singletonList(reviewItem), pageable, 1);
        when(reviewItemRepository.findByStatus(ReviewItemStatus.PENDING, pageable)).thenReturn(page);

        Page<ReviewItemDto> result = reviewQueueService.getReviewItemsByStatus(ReviewItemStatus.PENDING, pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(reviewItemId);
    }

    @Test
    void processReviewDecision_whenApproved_shouldUpdateStatusAndReturnDto() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.of(reviewItem));
        when(reviewItemRepository.save(any(ReviewItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewDecisionRequest request = new ReviewDecisionRequest();
        request.setStatus(ReviewItemStatus.APPROVED);

        ReviewItemDto result = reviewQueueService.processReviewDecision(reviewItemId, request);

        assertThat(result.getStatus()).isEqualTo(ReviewItemStatus.APPROVED);
        verify(reviewItemRepository).save(any(ReviewItem.class));
    }
    
    @Test
    void processReviewDecision_whenRejectedWithComments_shouldUpdateStatusAndReturnDto() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.of(reviewItem));
        when(reviewItemRepository.save(any(ReviewItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReviewDecisionRequest request = new ReviewDecisionRequest();
        request.setStatus(ReviewItemStatus.REJECTED);
        request.setComments("This is not compliant.");

        ReviewItemDto result = reviewQueueService.processReviewDecision(reviewItemId, request);

        assertThat(result.getStatus()).isEqualTo(ReviewItemStatus.REJECTED);
        assertThat(result.getComments()).isEqualTo("This is not compliant.");
        verify(reviewItemRepository).save(any(ReviewItem.class));
    }

    @Test
    void processReviewDecision_whenItemNotFound_shouldThrowResourceNotFoundException() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.empty());
        ReviewDecisionRequest request = new ReviewDecisionRequest();
        request.setStatus(ReviewItemStatus.APPROVED);

        assertThatThrownBy(() -> reviewQueueService.processReviewDecision(reviewItemId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ReviewItem not found with id: " + reviewItemId);
    }

    @Test
    void processReviewDecision_whenStatusIsPending_shouldThrowIllegalArgumentException() {
        ReviewDecisionRequest request = new ReviewDecisionRequest();
        request.setStatus(ReviewItemStatus.PENDING);
        
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.of(reviewItem));

        assertThatThrownBy(() -> reviewQueueService.processReviewDecision(reviewItemId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot change status to PENDING.");
    }
    
    @Test
    void processReviewDecision_whenRejectedWithoutComments_shouldThrowIllegalArgumentException() {
        when(reviewItemRepository.findById(reviewItemId)).thenReturn(Optional.of(reviewItem));

        ReviewDecisionRequest request = new ReviewDecisionRequest();
        request.setStatus(ReviewItemStatus.REJECTED);
        request.setComments(""); // or null

        assertThatThrownBy(() -> reviewQueueService.processReviewDecision(reviewItemId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Comments are required when rejecting a review item.");
    }

    // The test for getReviewStats has been removed as requested.
} 