package edu.jimei.praesidium.repository;

import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewItemRepository extends JpaRepository<ReviewItem, UUID> {

    /**
     * 根据状态分页查询审核项
     * @param status 审核状态
     * @param pageable 分页信息
     * @return 分页后的审核项列表
     */
    Page<ReviewItem> findByStatus(ReviewItemStatus status, Pageable pageable);
} 