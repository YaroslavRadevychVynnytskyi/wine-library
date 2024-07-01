package com.application.winelibrary.controller;

import com.application.winelibrary.dto.post.PostOfficesResponseDto;
import com.application.winelibrary.service.city.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "City management", description = "Endpoints for managing cities")
@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
@Profile("!test")
public class CityController {
    private final CityService cityService;

    @GetMapping
    @Operation(summary = "Get cities", description = "Retrieves all cities available in Ukraine")
    public List<String> getCities() {
        return cityService.getCities();
    }

    @GetMapping("/shipping-address")
    @Operation(summary = "Get post offices", description = "Provides post offices of the city")
    public PostOfficesResponseDto getPostOffices(@RequestParam String city) {
        return cityService.getShippingAddress(city);
    }
}
