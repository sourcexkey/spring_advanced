DROP  TABLE IF EXISTS user_accounts;
DROP  TABLE IF EXISTS tickets;
DROP  TABLE IF EXISTS users;
DROP  TABLE IF EXISTS events;

create table events (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title varchar(255),
    day DATE(255),
    ticket_price DECIMAL(20,2),
    PRIMARY KEY (ID)
);

create table users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name varchar(255),
    email varchar(255),
    PRIMARY KEY (ID)
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