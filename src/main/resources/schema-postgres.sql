DROP TABLE IF EXISTS articles;
CREATE TABLE articles(id serial PRIMARY KEY, title VARCHAR(255), main_topic VARCHAR(255), author VARCHAR(255),
    related_topics VARCHAR(255), article_link VARCHAR(255), is_active BOOLEAN NOT NULL, created_date TIMESTAMP  NOT NULL,
    last_modified_date TIMESTAMP NOT NULL, created_by BOOLEAN NOT NULL, last_modified_by BOOLEAN NOT NULL);

DROP TABLE IF EXISTS likes;
CREATE TABLE likes(id serial PRIMARY KEY, title VARCHAR(255), main_topic VARCHAR(255),
    is_active BOOLEAN NOT NULL, created_date TIMESTAMP NOT NULL, last_modified_date TIMESTAMP NOT NULL,
    created_by BOOLEAN NOT NULL, last_modified_by BOOLEAN NOT NULL);

DROP TABLE IF EXISTS recommendations;
CREATE TABLE recommendations(id serial PRIMARY KEY, article_id FLOAT, similarity_score FLOAT,
    is_active BOOLEAN NOT NULL, created_date TIMESTAMP NOT NULL, last_modified_date TIMESTAMP NOT NULL,
    created_by BOOLEAN NOT NULL, last_modified_by BOOLEAN NOT NULL);

DROP TABLE IF EXISTS urls;
CREATE TABLE urls(id serial PRIMARY KEY, article_link VARCHAR(255),
    is_active BOOLEAN NOT NULL, created_date TIMESTAMP NOT NULL, last_modified_date TIMESTAMP NOT NULL,
    created_by BOOLEAN NOT NULL, last_modified_by BOOLEAN NOT NULL);

DROP TABLE IF EXISTS users;
CREATE TABLE users(id serial PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255),
    token_expired BOOLEAN, roles TEXT[], is_active BOOLEAN NOT NULL, created_date TIMESTAMP NOT NULL,
    last_modified_date TIMESTAMP NOT NULL, created_by BOOLEAN NOT NULL, last_modified_by BOOLEAN NOT NULL)