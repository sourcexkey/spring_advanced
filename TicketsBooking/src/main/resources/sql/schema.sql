DROP  TABLE IF EXISTS user_accounts;
DROP  TABLE IF EXISTS tickets;
DROP  TABLE IF EXISTS users_details;
DROP  TABLE IF EXISTS users;
DROP  TABLE IF EXISTS events;
DROP  TABLE IF EXISTS persistent_logins;

CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

create table events (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title varchar(255),
    day DATE,
    ticket_price DECIMAL(20,2),
    PRIMARY KEY (ID)
);

create table users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    email varchar(255),
    PRIMARY KEY (ID)
);

create table users_details(
    user_id BIGINT NOT NULL,
    password VARCHAR(255),
    enabled BOOLEAN,
    account_non_expired BOOLEAN,
    credential_non_expired BOOLEAN,
    account_non_locked BOOLEAN,
    authorities VARCHAR(255) DEFAULT 'ROLE_REGISTERED_USER',
    FOREIGN KEY (user_id) REFERENCES users(id)
);


create table user_accounts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    currency DECIMAL(20,2),
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

create table tickets (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL ,
    event_id BIGINT NOT NULL,
    category VARCHAR(255),
    place INT,
    PRIMARY KEY (ID),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);