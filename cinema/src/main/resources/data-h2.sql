INSERT INTO genre(name) VALUES ('SF');

INSERT INTO movie(description, duration, rating, title, genre_id) VALUES ('test', 61, 'G', 'Movie 1', 1);
INSERT INTO movie(description, duration, rating, title, genre_id) VALUES ('test', 62, 'G', 'Movie 2', 1);
INSERT INTO movie(description, duration, rating, title, genre_id) VALUES ('test', 63, 'G', 'Movie 3', 1);

INSERT INTO hall(name) VALUES('Big Hall');
INSERT INTO seat(number, row_num, hall_id) VALUES (1, 1, 1);
INSERT INTO seat(number, row_num, hall_id) VALUES (2, 1, 1);
INSERT INTO seat(number, row_num, hall_id) VALUES (1, 2, 1);
INSERT INTO seat(number, row_num, hall_id) VALUES (2, 2, 1);

INSERT INTO screening(price, start_time, hall_id, movie_id)
VALUES(20, PARSEDATETIME('2025-06-10 14:30:00.000000', 'yyyy-MM-dd HH:mm:ss.SSSSSS'), 1, 1);

INSERT INTO screening(price, start_time, hall_id, movie_id)
VALUES(20, PARSEDATETIME('2025-06-10 16:30:00.000000', 'yyyy-MM-dd HH:mm:ss.SSSSSS'), 1, 2);




