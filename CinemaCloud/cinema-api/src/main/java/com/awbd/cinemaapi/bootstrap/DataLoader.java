package com.awbd.cinemaapi.bootstrap;

import com.awbd.cinemaapi.domain.security.Authority;
import com.awbd.cinemaapi.domain.security.User;
import com.awbd.cinemaapi.repositories.security.AuthorityRepository;
import com.awbd.cinemaapi.repositories.security.UserRepository;
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
    private final PasswordEncoder passwordEncoder;


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

    @Override
    public void run(String... args) {
        loadUserData();
        log.info("Data loaded");
    }
}
