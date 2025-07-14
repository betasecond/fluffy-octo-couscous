ALTER TABLE review_items
ADD COLUMN original_query TEXT,
ADD COLUMN current_answer TEXT,
ADD COLUMN suggested_answer TEXT,
ADD COLUMN standard_question VARCHAR(255);

ALTER TABLE review_items
DROP COLUMN knowledge_id,
DROP COLUMN content; 