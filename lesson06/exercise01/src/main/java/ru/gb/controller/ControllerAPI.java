package ru.gb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gb.domain.Result;
import ru.gb.service.ServiceApi;

@Controller
public class ControllerAPI {
    @Autowired
    private ServiceApi serviceApi;

    @GetMapping("/")
    public String getCharacters(Model model)
    {
        Iterable<Result> results = serviceApi.getAllCharacters().getResults();
        model.addAttribute("records", results);

        return "index";
    }
}
