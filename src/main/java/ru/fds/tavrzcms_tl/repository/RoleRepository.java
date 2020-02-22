package ru.fds.tavrzcms_tl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fds.tavrzcms_tl.domain.AppUser;
import ru.fds.tavrzcms_tl.domain.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByAppUsers(AppUser appUser);
}
