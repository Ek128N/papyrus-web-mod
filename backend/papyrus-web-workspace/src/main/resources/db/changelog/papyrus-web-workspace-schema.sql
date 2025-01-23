CREATE TABLE workspace (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    project_id UUID NOT NULL,
    is_owner BOOLEAN NOT NULL,
    CONSTRAINT pk_workspace_id PRIMARY KEY (id)
);