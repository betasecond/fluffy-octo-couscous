-- Insert a PENDING review item
INSERT INTO review_items (id, knowledge_id, content, status, reviewer_id, comments, created_at, updated_at)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'knowledge-001', 'Content for a pending review item. Needs approval.', 'PENDING', NULL, NULL, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert an APPROVED review item
INSERT INTO review_items (id, knowledge_id, content, status, reviewer_id, comments, created_at, updated_at)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d480', 'knowledge-002', 'This content has already been approved.', 'APPROVED', 'reviewer-01', 'Looks good.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert a REJECTED review item
INSERT INTO review_items (id, knowledge_id, content, status, reviewer_id, comments, created_at, updated_at)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d481', 'knowledge-003', 'This content was rejected due to compliance issues.', 'REJECTED', 'reviewer-02', 'Contains sensitive information.', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP); 