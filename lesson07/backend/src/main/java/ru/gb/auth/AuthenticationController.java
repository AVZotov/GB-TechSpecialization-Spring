package ru.gb.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("customers/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,
                response.token()).body(response);
    }
}
