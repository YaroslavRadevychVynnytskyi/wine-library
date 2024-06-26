package com.application.winelibrary.controller;

import com.application.winelibrary.service.city.CityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Profile("!test")
public class CityController {
    private final CityService cityService;

    @GetMapping
    public List<String> getCities() {
        return cityService.getCities();
    }
}
