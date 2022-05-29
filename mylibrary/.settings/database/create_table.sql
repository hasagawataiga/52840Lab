DROP DATABASE new_library;
CREATE DATABASE new_library;

CREATE TABLE new_library.book(
	id bigint NOT NULL PRIMARY KEY auto_increment,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    createddate TIMESTAMP NULL,
    modifieddate TIMESTAMP NULL,
    createdby VARCHAR(150) NULL,
    modifiedby VARCHAR(150) NULL
);
INSERT INTO new_library.book(
	title,
	author
)
VALUES
('Book1','Author1'),
('Book2','Author2'),
('Book3','Author3'),
('Book4','Author4')
;
