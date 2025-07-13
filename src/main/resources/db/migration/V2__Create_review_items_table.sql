CREATE TABLE review_items (
    id UUID PRIMARY KEY,
    knowledge_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    reviewer_id VARCHAR(255),
    comments TEXT,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

COMMENT ON TABLE review_items IS '知识库内容审核项';
COMMENT ON COLUMN review_items.id IS '主键ID';
COMMENT ON COLUMN review_items.knowledge_id IS '关联的知识点ID';
COMMENT ON COLUMN review_items.content IS '需要审核的内容';
COMMENT ON COLUMN review_items.status IS '审核状态 (PENDING, APPROVED, REJECTED)';
COMMENT ON COLUMN review_items.reviewer_id IS '审核员ID';
COMMENT ON COLUMN review_items.comments IS '审核意见';
COMMENT ON COLUMN review_items.created_at IS '创建时间';
COMMENT ON COLUMN review_items.updated_at IS '更新时间'; 