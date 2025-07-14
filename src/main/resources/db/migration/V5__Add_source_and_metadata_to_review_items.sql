ALTER TABLE review_items
ADD COLUMN source VARCHAR(255),
ADD COLUMN metadata jsonb; 