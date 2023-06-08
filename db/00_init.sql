DROP DATABASE IF EXISTS library;
CREATE DATABASE library;

\c library;

DROP TABLE IF EXISTS book;
CREATE TABLE book (
    id   BIGINT NOT NULL,
    name VARCHAR(8192),
    PRIMARY KEY (id)
);