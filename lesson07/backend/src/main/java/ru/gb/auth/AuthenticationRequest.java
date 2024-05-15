package ru.gb.auth;

public record AuthenticationRequest(
        String username,
        String password
) { }
