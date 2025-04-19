-- table book
CREATE TABLE book (
    book_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(45) NOT NULL,
    author VARCHAR(45) NOT NULL,
    release_date DATE NOT NULL,
    PRIMARY KEY (book_id)
);

-- table person
CREATE TABLE person (
    person_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    cep CHAR(9) NOT NULL,
    PRIMARY KEY (person_id)
);

-- relationship table loan
CREATE TABLE loan (
    loan_id INT NOT NULL AUTO_INCREMENT,
    book_id INT NOT NULL,
    person_id INT NOT NULL,
    loan_date DATE NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book(book_id),
    FOREIGN KEY (person_id) REFERENCES person(person_id),
    PRIMARY KEY (loan_id)
);