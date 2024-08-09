CREATE TABLE profile ( 
    id UUID NOT NULL,
    content TEXT NOT NULL,
	CONSTRAINT pk_profile_id PRIMARY KEY (id)
);