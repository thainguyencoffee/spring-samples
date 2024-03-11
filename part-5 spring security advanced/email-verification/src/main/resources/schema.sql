
CREATE TABLE courses (
     id          BIGINT NOT NULL auto_increment,
     category    VARCHAR(255),
     description VARCHAR(255),
     name        VARCHAR(255),
     rating      INTEGER NOT NULL,
     PRIMARY KEY (id)
);

create table ct_users(
    id BIGINT NOT NULL auto_increment,
    first_name varchar(50),
    last_name varchar(50),
    email varchar(50),
    username varchar(50),
    password varchar(100),
    verified boolean,
    PRIMARY KEY (id)
);

CREATE TABLE CT_EMAIL_VERIFICATIONS
(
    verification_id VARCHAR(50),
    username        VARCHAR(50),
    PRIMARY KEY (verification_id)
);

