use new_library;

CREATE TABLE book(
	id bigint NOT NULL PRIMARY KEY auto_increment,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(150) NOT NULL,
    createddate TIMESTAMP NULL,
    modifieddate TIMESTAMP NULL,
    createdby VARCHAR(150) NULL,
    modifiedby VARCHAR(150) NULL
);
