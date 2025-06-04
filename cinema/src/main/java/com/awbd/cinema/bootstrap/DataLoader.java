package com.awbd.cinema.bootstrap;

import com.awbd.cinema.domain.Genre;
import com.awbd.cinema.domain.Hall;
import com.awbd.cinema.domain.Seat;
import com.awbd.cinema.domain.security.Authority;
import com.awbd.cinema.domain.security.User;
import com.awbd.cinema.repositories.GenreRepository;
import com.awbd.cinema.repositories.HallRepository;
import com.awbd.cinema.repositories.SeatRepository;
import com.awbd.cinema.repositories.security.AuthorityRepository;
import com.awbd.cinema.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PasswordEncoder passwordEncoder;
    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;


    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_USER").build());

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }

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
                            .row(row)
                            .number(num)
                            .build();

                    Seat secondHallSeat = Seat.builder()
                            .hall(secondHall)
                            .row(row)
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
        loadUserData();
        loadGenres();
        loadHalls();
        log.info("Data loaded");
    }
}
