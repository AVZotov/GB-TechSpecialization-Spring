package ru.gb.auth;

import ru.gb.domain.CustomerDTO;

public record AuthenticationResponse(String token,
                                     CustomerDTO customerDTO) {
}
