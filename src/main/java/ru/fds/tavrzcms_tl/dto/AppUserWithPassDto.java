package ru.fds.tavrzcms_tl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserWithPassDto {

    private Long userId;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    private Long employeeId;

    private List<String> appRoles;
}
