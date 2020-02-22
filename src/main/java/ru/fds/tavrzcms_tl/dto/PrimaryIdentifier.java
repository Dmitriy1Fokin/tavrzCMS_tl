package ru.fds.tavrzcms_tl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class PrimaryIdentifier {
    private final String name;
    private final String typeOfPrimaryIdentifier;

}
