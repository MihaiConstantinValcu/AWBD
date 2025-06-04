package com.awbd.screeningapi.bootstrap;

import com.awbd.screeningapi.domain.Genre;
import com.awbd.screeningapi.domain.Hall;
import com.awbd.screeningapi.domain.Seat;
import com.awbd.screeningapi.repositories.GenreRepository;
import com.awbd.screeningapi.repositories.HallRepository;
import com.awbd.screeningapi.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final GenreRepository genreRepository;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;


    private void loadGenres() {
        if (genreRepository.count() == 0) {
            Genre actionGenre = Genre.builder()
                    .name("Action")
                    .build();

            Genre fantasyGenre = Genre.builder()
                    .name("Fantasy")
                    .build();

            Genre comedyGenre = Genre.builder()
                    .name("Comedy")
                    .build();

            genreRepository.save(actionGenre);
            genreRepository.save(fantasyGenre);
            genreRepository.save(comedyGenre);
        }
    }

    private void loadHalls() {
        if (hallRepository.count() == 0) {
            Hall firstHall = Hall.builder()
                    .name("1")
                    .build();

            Hall secondHall = Hall.builder()
                    .name("2")
                    .build();

            hallRepository.save(firstHall);
            hallRepository.save(secondHall);

            for (int row = 0; row < 10; row++) {
                for (int num = 0; num < 10; num++) {
                    Seat firstHallSeat = Seat.builder()
                            .hall(firstHall)
                            .row_num(row)
                            .number(num)
                            .build();

                    Seat secondHallSeat = Seat.builder()
                            .hall(secondHall)
                            .row_num(row)
                            .number(num)
                            .build();

                    seatRepository.save(firstHallSeat);
                    seatRepository.save(secondHallSeat);
                }
            }
        }
    }


    @Override
    public void run(String... args) {
        loadGenres();
        loadHalls();
        log.info("Data loaded");
    }
}