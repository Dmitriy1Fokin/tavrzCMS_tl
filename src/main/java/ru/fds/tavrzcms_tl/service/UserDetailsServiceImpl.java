package ru.fds.tavrzcms_tl.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fds.tavrzcms_tl.domain.AppUser;
import ru.fds.tavrzcms_tl.dto.EmployeeDto;
import ru.fds.tavrzcms_tl.repository.RoleRepository;
import ru.fds.tavrzcms_tl.repository.AppUserRepository;
import ru.fds.tavrzcms_tl.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(AppUserRepository appUserRepository,
                                  RoleRepository roleRepository,
                                  EmployeeService employeeService,
                                  PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String name){
        return appUserRepository.findByNickname(name).map(appUser -> {
            List<Role> roleList = roleRepository.findAllByAppUsers(appUser);

            List<GrantedAuthority> authorityList = roleList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            return new User(appUser.getNickname(), appUser.getPassword(), authorityList);
        }).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public boolean checkIfValidOldPassword(final String newPassword, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, newPassword);
    }

    public Optional<AppUser> getAppUser(Long userId){
        return appUserRepository.findById(userId);
    }

    @Transactional
    public void updatePassword(User user, String password){
       appUserRepository.findByNickname(user.getUsername()).ifPresent(appUser -> {
           appUser.setPassword(passwordEncoder.encode(password));
           appUserRepository.save(appUser);
       });
    }

    public void resetPassword(AppUser appUser, String password){
        appUser.setPassword(passwordEncoder.encode(password));
        appUserRepository.save(appUser);
    }

    @Transactional
    public void insertUser(AppUser appUser){
        appUserRepository.save(appUser);
    }

    @Transactional
    public void updateUser(AppUser appUser){
        insertUser(appUser);
    }

    @Transactional
    public void insertEmployeeUser(AppUser appUser, EmployeeDto employeeDto){
        employeeDto = employeeService.insertEmployee(employeeDto);
        appUser.setEmployeeId(employeeDto.getEmployeeId());
        appUserRepository.save(appUser);
    }

    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll(Sort.by("nickname"));
    }
}
