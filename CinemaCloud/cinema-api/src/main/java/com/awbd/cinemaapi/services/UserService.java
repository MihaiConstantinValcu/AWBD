package com.awbd.cinemaapi.services;

import com.awbd.cinemaapi.domain.security.User;
import com.awbd.cinemaapi.dtos.UserDto;
import com.awbd.cinemaapi.repositories.security.AuthorityRepository;
import com.awbd.cinemaapi.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserDto userDto) throws Exception {

        boolean checkExistingUsername = userRepository.findByUsername(userDto.getUsername()).isPresent();
        if (checkExistingUsername) {
            throw new Exception("Username-ul este luat");
        }

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authority(authorityRepository.findByRole("ROLE_USER"))
                .build();

        userRepository.save(user);
    }
}
