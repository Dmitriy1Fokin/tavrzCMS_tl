package ru.fds.tavrzcms_tl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fds.tavrzcms_tl.domain.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByNickname(String nickname);
}
