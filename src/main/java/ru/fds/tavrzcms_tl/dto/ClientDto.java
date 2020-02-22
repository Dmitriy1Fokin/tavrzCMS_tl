package ru.fds.tavrzcms_tl.dto;

import lombok.Getter;
import lombok.ToString;
import ru.fds.tavrzcms_tl.dictionary.TypeOfClient;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@ToString
public class ClientDto{

    private Long clientId;

    @NotNull
    private TypeOfClient typeOfClient;

    @NotNull
    private Long clientManagerId;

    @NotNull
    private Long employeeId;

    @Valid
    private ClientLegalEntityDto clientLegalEntityDto;

    @Valid
    private ClientIndividualDto clientIndividualDto;
}
