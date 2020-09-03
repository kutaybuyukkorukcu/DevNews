CREATE TABLE articles
(
    id SERIAL PRIMARY KEY NOT NULL ,
    title VARCHAR(255),
    main_topic VARCHAR(255),
    author VARCHAR(255),
    related_topics VARCHAR(255),
    article_link VARCHAR(255),
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE likes
(
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255),
    main_topic VARCHAR(255),
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE recommendations
(
    id SERIAL PRIMARY KEY NOT NULL,
    article_id FLOAT4,
    similarity_score FLOAT4,
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE urls
(
    id SERIAL PRIMARY KEY NOT NULL,
    article_link VARCHAR(255),
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    enabled BOOL,
    token_expired BOOL,
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE users_roles
(
    id SERIAL PRIMARY KEY NOT NULL,
    user_id FLOAT4,
    role_id FLOAT4,
    FOREIGN KEY (user_id)
    REFERENCES users (id),
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
);

CREATE TABLE privileges
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(255),
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE roles_privileges
(
    id SERIAL PRIMARY KEY NOT NULL,
    role_id FLOAT4,
    privilege_id FLOAT4,
    FOREIGN KEY (role_id)
    REFERENCES roles (id),
    FOREIGN KEY (privilege_id)
    REFERENCES privileges (id)
);

CREATE TABLE roles
(
    id SERIAL PRIMARY KEY NOT NULL,
    is_active BOOL NOT NULL,
    created_date DATE NOT NULL,
    last_modified_date DATE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE verification_token
(
    id SERIAL PRIMARY KEY,
    token VARCHAR(255),
    user_id FLOAT,
    expiry_date DATE,
    FOREIGN KEY (user_id)
    REFERENCES users (id)
--     is_active BOOL,
--     created_date DATE NOT NULL,
--     last_modified_date DATE NOT NULL,
--     created_by VARCHAR(255) NOT NULL,
--     last_modified_by VARCHAR(255) NOT NULL
);

CREATE TABLE password_reset_tokens
(
    id SERIAL PRIMARY KEY NOT NULL,
    token VARCHAR(255),
    user_id FLOAT,
    expiry_date DATE,
    FOREIGN KEY (user_id)
    REFERENCES users (id)
--     is_active BOOL,
--     created_date DATE NOT NULL,
--     last_modified_date DATE NOT NULL,
--     created_by VARCHAR(255) NOT NULL,
--     last_modified_by VARCHAR(255) NOT NULL
);