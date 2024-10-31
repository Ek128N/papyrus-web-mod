CREATE TABLE authenticateduser (
    id UUID NOT NULL,
    name VARCHAR(50) NOT NULL,
    pass VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
CONSTRAINT pk_authenticated_user_id PRIMARY KEY (id),
CONSTRAINT unique_name UNIQUE (name)
);