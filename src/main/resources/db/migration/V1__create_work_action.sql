CREATE TABLE work_action
(
    id           UNIQUEIDENTIFIER NOT NULL PRIMARY KEY,
    reference_id VARCHAR(100)     NOT NULL UNIQUE,
    title        VARCHAR(200)     NOT NULL,
    description  VARCHAR(2000) NULL,
    status       VARCHAR(30)      NOT NULL,
    priority     VARCHAR(20)      NOT NULL,
    due_date     DATE NULL,
    assigned_to  VARCHAR(150) NULL,
    created_at   DATETIME2        NOT NULL,
    updated_at   DATETIME2        NOT NULL
);
CREATE INDEX ix_work_action_status ON work_action (status);
CREATE INDEX ix_work_action_assigned_to ON work_action (assigned_to);
