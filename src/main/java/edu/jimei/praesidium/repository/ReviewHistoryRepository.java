package edu.jimei.praesidium.repository;

import edu.jimei.praesidium.entity.ReviewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewHistoryRepository extends JpaRepository<ReviewHistory, UUID> {
    List<ReviewHistory> findByReviewItemIdOrderByCreatedAtAsc(UUID reviewItemId);
} 