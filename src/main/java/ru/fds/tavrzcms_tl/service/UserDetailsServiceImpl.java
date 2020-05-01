package ru.fds.tavrzcms_tl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.repository.RoleRepository;
import ru.fds.tavrzcms_tl.repository.AppUserRepository;
import ru.fds.tavrzcms_tl.domain.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String name){
        return appUserRepository.findByName(name).map(appUser -> {
            List<Role> roleList = roleRepository.findAllByAppUsers(appUser);

            List<GrantedAuthority> authorityList = roleList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new User(appUser.getName(), appUser.getPassword(), authorityList);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Transactional
    public void updatePassword(User user, String password){
       appUserRepository.findByName(user.getUsername()).ifPresent(appUser -> {
           appUser.setPassword(passwordEncoder.encode(password));
           appUserRepository.save(appUser);
       });
    }
}
