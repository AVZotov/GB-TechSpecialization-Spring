package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.gb.domain.Characters;

import java.util.List;

@Service
public class ServiceApiImpl implements ServiceApi{

    @Autowired
    private RestTemplate template;

    @Autowired
    private HttpHeaders headers;

    private  static final String CHARACTER_API = "https://rickandmortyapi.com/api/character";

    @Override
    public Characters getAllCharacters() {
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Characters> response = template.exchange(CHARACTER_API, HttpMethod.GET,entity, Characters.class);

        return response.getBody();
    }
}
