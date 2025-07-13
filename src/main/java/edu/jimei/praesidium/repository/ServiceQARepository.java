package edu.jimei.praesidium.repository;

import edu.jimei.praesidium.entity.ServiceQA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing ServiceQA data.
 */
@Repository
public interface ServiceQARepository extends JpaRepository<ServiceQA, Long> {
} 