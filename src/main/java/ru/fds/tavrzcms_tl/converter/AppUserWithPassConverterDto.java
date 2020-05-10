package ru.fds.tavrzcms_tl.converter;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.fds.tavrzcms_tl.domain.AppUser;
import ru.fds.tavrzcms_tl.domain.Role;
import ru.fds.tavrzcms_tl.dto.AppUserWithPassDto;
import ru.fds.tavrzcms_tl.exception.NotFoundException;
import ru.fds.tavrzcms_tl.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AppUserWithPassConverterDto implements ConverterDto<AppUser, AppUserWithPassDto> {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserWithPassConverterDto(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser toEntity(AppUserWithPassDto dto) {
        List<Role> roleList = dto.getAppRoles()
                .stream()
                .map(s -> roleRepository.findByName(s).orElseThrow(() -> new NotFoundException("role not found")))
                .collect(Collectors.toList());

        return AppUser.builder()
                .userId(dto.getUserId())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .employeeId(dto.getEmployeeId())
                .appRoles(roleList)
                .build();
    }

    @Override
    public AppUserWithPassDto toDto(AppUser entity) {
        return AppUserWithPassDto.builder()
                .userId(entity.getUserId())
                .nickname(entity.getNickname())
                .password(entity.getPassword())
                .employeeId(entity.getEmployeeId())
                .appRoles(entity.getAppRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
