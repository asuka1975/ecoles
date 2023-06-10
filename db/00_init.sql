DROP DATABASE IF EXISTS library;
CREATE DATABASE library;

\c library;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS author (
    id      SERIAL NOT NULL,
    name    TEXT   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book (
    id          SERIAL NOT NULL,
    title       TEXT   NOT NULL,
    created_at  DATE   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_detail (
    book_id     SERIAL NOT NULL,
    author_id   SERIAL NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id)
        REFERENCES book (id),
    FOREIGN KEY (author_id)
        REFERENCES author (id)
);

CREATE TABLE IF NOT EXISTS customer (
    id          UUID DEFAULT uuid_generate_v4(),
    name        TEXT NOT NULL,
    email       TEXT NOT NULL UNIQUE,
    created_at  DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reservation (
    id          BIGSERIAL NOT NULL,
    customer_id UUID NOT NULL,
    reserved_at DATE NOT NULL,
    rental_at   DATE NOT NULL CHECK(rental_at > reserved_at),
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customer (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservation_detail (
    reservation_id  BIGSERIAL NOT NULL,
    detail_id       SERIAL NOT NULL,
    book_id         SERIAL NOT NULL,
    customer_id     UUID NOT NULL,
    PRIMARY KEY (reservation_id, detail_id),
    FOREIGN KEY (book_id)
        REFERENCES book (id)
        ON DELETE CASCADE,
    FOREIGN KEY (customer_id)
        REFERENCES customer (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rental (
    id          BIGSERIAL NOT NULL,
    customer_id UUID NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id)
        REFERENCES customer (id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS rental_detail (
    rental_id   BIGSERIAL NOT NULL,
    detail_id   SERIAL NOT NULL,
    book_id     SERIAL NOT NULL,
    customer_id UUID NOT NULL,
    rental_at   DATE NOT NULL,
    PRIMARY KEY (rental_id, detail_id),
    FOREIGN KEY (book_id)
        REFERENCES book (id)
        ON DELETE CASCADE,
    FOREIGN KEY (customer_id)
        REFERENCES customer (id)
        ON DELETE CASCADE
);
