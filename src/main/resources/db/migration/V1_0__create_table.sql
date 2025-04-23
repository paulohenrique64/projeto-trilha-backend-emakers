-- table user
CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    cep CHAR(9) NOT NULL,
    state VARCHAR(80) NOT NULL,
    PRIMARY KEY (user_id)
);

-- table book
CREATE TABLE book (
    book_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(45) NOT NULL,
    author VARCHAR(45) NOT NULL,
    release_date DATE NOT NULL,
    PRIMARY KEY (book_id)
);

-- relationship table loan
CREATE TABLE loan (
    loan_id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    user_id INT NOT NULL,
    loan_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(book_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    PRIMARY KEY (loan_id)
);