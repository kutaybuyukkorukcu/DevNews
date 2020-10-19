CREATE TABLE stories
(
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255),
    main_topic VARCHAR(255),
    url VARCHAR(255),
    author VARCHAR(255),
    text VARCHAR(255),
    upvote_count integer,
    comment_count integer,
    created integer,
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE comments
(
    id SERIAL PRIMARY KEY NOT NULL,
    story_id integer,
    author VARCHAR(255),
    text VARCHAR(255),
    parent_comment_id integer,
    created integer,
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (story_id)
        REFERENCES articles (id)
);
