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
public class AppUserWithoutPassDto {

    private Long userId;

    @NotBlank
    private String nickname;

    private Long employeeId;

    private List<String> appRoles;
}
