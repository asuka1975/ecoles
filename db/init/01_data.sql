\c library;

\COPY author (name) FROM '/data/author.csv' DELIMITER ',' CSV HEADER;
\COPY book (title, created_at) FROM '/data/book.csv' DELIMITER ',' CSV HEADER;
\COPY book_detail FROM '/data/book_detail.csv' DELIMITER ',' CSV HEADER;
\COPY customer FROM '/data/customer.csv' DELIMITER ',' CSV HEADER;