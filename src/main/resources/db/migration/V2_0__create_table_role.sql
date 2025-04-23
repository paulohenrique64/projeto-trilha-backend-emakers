CREATE TABLE role (
    role_id INT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id)
);

INSERT INTO role (role_name) VALUES ('ROLE_ADMIN');

INSERT INTO role (role_name) VALUES ('ROLE_USER');