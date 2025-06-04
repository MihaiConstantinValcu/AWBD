CREATE TABLE genre (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
);

CREATE TABLE movie (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       description VARCHAR(500),
                       duration INT,
                       rating VARCHAR(10),
                       title VARCHAR(255),
                       genre_id BIGINT,
                       CONSTRAINT fk_movie_genre FOREIGN KEY (genre_id) REFERENCES genre(id)
);

CREATE TABLE hall (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE seat (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      number INT NOT NULL,
                      row_num INT NOT NULL,
                      hall_id BIGINT NOT NULL,
                      CONSTRAINT fk_seat_hall FOREIGN KEY (hall_id) REFERENCES hall(id)
);

CREATE TABLE screening (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           price DECIMAL(10, 2) NOT NULL,
                           start_time TIMESTAMP(6) NOT NULL,
                           hall_id BIGINT NOT NULL,
                           movie_id BIGINT NOT NULL,
                           CONSTRAINT fk_screening_hall FOREIGN KEY (hall_id) REFERENCES hall(id),
                           CONSTRAINT fk_screening_movie FOREIGN KEY (movie_id) REFERENCES movie(id)
);
