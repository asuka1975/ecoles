\c library;

\COPY author FROM '/data/author.csv' DELIMITER ',' CSV HEADER;
\COPY book FROM '/data/book.csv' DELIMITER ',' CSV HEADER;
\COPY book_detail FROM '/data/book_detail.csv' DELIMITER ',' CSV HEADER;