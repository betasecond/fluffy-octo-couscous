package edu.jimei.praesidium.repository;

import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewItemRepository extends JpaRepository<ReviewItem, UUID>, JpaSpecificationExecutor<ReviewItem> {

    /**
     * 根据状态分页查询审核项
     * @param status 审核状态
     * @param pageable 分页信息
     * @return 分页后的审核项列表
     */
    Page<ReviewItem> findByStatus(ReviewItemStatus status, Pageable pageable);

    long countByStatus(ReviewItemStatus status);

    @Query("SELECT r.source, COUNT(r) FROM ReviewItem r WHERE r.source IS NOT NULL GROUP BY r.source")
    List<Object[]> countBySource();

    @Query("SELECT DISTINCT r.source FROM ReviewItem r WHERE r.source IS NOT NULL")
    List<String> findDistinctSources();
} 