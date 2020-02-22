package ru.fds.tavrzcms_tl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.fds.tavrzcms_tl.repository.RoleRepository;
import ru.fds.tavrzcms_tl.repository.UserRepository;
import ru.fds.tavrzcms_tl.domain.AppUser;
import ru.fds.tavrzcms_tl.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDetailsServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name){
        Optional<AppUser> appUser = userRepository.findByName(name);

        List<Role> roleList = appUser.map(roleRepository::findAllByAppUsers)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<GrantedAuthority> authorityList = roleList.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        roleList.forEach(role -> {
//            authorityList.add(new SimpleGrantedAuthority(role.getName()));
//        });

        return new User(appUser.get().getName(), appUser.get().getPassword(), authorityList);
    }
}
