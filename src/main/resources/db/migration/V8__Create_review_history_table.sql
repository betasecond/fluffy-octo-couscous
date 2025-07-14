CREATE TABLE review_history (
    id UUID PRIMARY KEY,
    review_item_id UUID NOT NULL,
    action VARCHAR(255) NOT NULL,
    comment TEXT,
    reviewer_id VARCHAR(255),
    reviewer_name VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
); 