package edu.jimei.praesidium.repository.specifications;

import edu.jimei.praesidium.entity.ReviewItem;
import edu.jimei.praesidium.enums.ReviewItemStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ReviewItemSpecification {

    public static Specification<ReviewItem> hasStatus(ReviewItemStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<ReviewItem> hasSource(String source) {
        return (root, query, criteriaBuilder) ->
                !StringUtils.hasText(source) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("source"), source);
    }
} 