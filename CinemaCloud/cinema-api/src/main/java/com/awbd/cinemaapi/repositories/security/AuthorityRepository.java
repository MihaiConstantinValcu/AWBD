package com.awbd.cinemaapi.repositories.security;

import com.awbd.cinemaapi.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByRole(String role);
}
