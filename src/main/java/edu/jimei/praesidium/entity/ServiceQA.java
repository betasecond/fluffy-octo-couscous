package edu.jimei.praesidium.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "service_qa")
@Data
public class ServiceQA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String answer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "service_qa_keywords", joinColumns = @JoinColumn(name = "service_qa_id"))
    @Column(name = "keyword")
    private List<String> keywords;
} 