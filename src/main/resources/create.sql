CREATE TABLE comments
(
    id SERIAL PRIMARY KEY NOT NULL,
    article_id integer,
    author VARCHAR(255),
    text VARCHAR(255),
    parent_comment_id VARCHAR(255),
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL,
    FOREIGN KEY (article_id)
        REFERENCES articles (id)
);