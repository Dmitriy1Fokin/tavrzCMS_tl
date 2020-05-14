package ru.fds.tavrzcms_tl.converter;

import org.springframework.stereotype.Component;
import ru.fds.tavrzcms_tl.domain.AppUser;
import ru.fds.tavrzcms_tl.domain.Role;
import ru.fds.tavrzcms_tl.dto.AppUserWithoutPassDto;
import ru.fds.tavrzcms_tl.exception.NotFoundException;
import ru.fds.tavrzcms_tl.repository.AppUserRepository;
import ru.fds.tavrzcms_tl.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class AppUserWithoutPassConverterDto implements ConverterDto<AppUser, AppUserWithoutPassDto> {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;

    public AppUserWithoutPassConverterDto(RoleRepository roleRepository,
                                          AppUserRepository appUserRepository) {
        this.roleRepository = roleRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser toEntity(AppUserWithoutPassDto dto) {
        AppUser appUser = appUserRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        List<Role> roleList = dto.getAppRoles()
                .stream()
                .map(s -> roleRepository.findByName(s).orElseThrow(() -> new NotFoundException("role not found")))
                .collect(Collectors.toList());

        return AppUser.builder()
                .userId(dto.getUserId())
                .nickname(dto.getNickname())
                .password(appUser.getPassword())
                .employeeId(dto.getEmployeeId())
                .appRoles(roleList)
                .build();
    }

    @Override
    public AppUserWithoutPassDto toDto(AppUser entity) {
        return AppUserWithoutPassDto.builder()
                .userId(entity.getUserId())
                .nickname(entity.getNickname())
                .employeeId(entity.getEmployeeId())
                .appRoles(entity.getAppRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .build();
    }
}
