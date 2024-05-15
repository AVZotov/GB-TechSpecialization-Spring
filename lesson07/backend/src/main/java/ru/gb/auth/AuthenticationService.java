package ru.gb.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.gb.domain.Customer;
import ru.gb.domain.CustomerDTO;
import ru.gb.jwt.JwtUtil;
import ru.gb.service.CustomerDtoMapper;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomerDtoMapper customerDtoMapper;
    private final JwtUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );

        Customer principal = (Customer) authentication.getPrincipal();
        CustomerDTO customerDTO = customerDtoMapper.apply(principal);

        String token =
                jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());

        return new AuthenticationResponse(token, customerDTO);
    }
}
