CREATE TABLE users_roles
(
    id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER,
    role_id INTEGER,
    FOREIGN KEY (user_id)
        REFERENCES users (id),
    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);