ALTER TABLE users
    ADD COLUMN role_id INT,
    ADD CONSTRAINT fk_users_role
        FOREIGN KEY (role_id)
        REFERENCES role(role_id);
