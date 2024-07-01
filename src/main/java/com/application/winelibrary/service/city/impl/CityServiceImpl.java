package com.application.winelibrary.service.city.impl;

import com.application.winelibrary.dto.post.PostOfficeDto;
import com.application.winelibrary.dto.post.PostOfficesResponseDto;
import com.application.winelibrary.entity.City;
import com.application.winelibrary.mapper.PostOfficeMapper;
import com.application.winelibrary.repository.city.CityRepository;
import com.application.winelibrary.service.city.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class CityServiceImpl implements CityService {
    @Value("${geonames.url}")
    private String getGeoNamesUrl;

    private final RestOperations restTemplate;
    private final ObjectMapper objectMapper;

    private final CityRepository cityRepository;
    private final PostOfficeMapper postOfficeMapper;

    @Override
    public List<String> getCities() {
        String response = restTemplate.getForObject(getGeoNamesUrl, String.class);

        List<String> cities = new ArrayList<>();
        JsonNode root = null;
        try {
            root = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode geonames = root.path("geonames");

        if (geonames.isArray()) {
            geonames.forEach(cityNode -> cities.add(cityNode.path("name").asText()));
        }
        return cities;
    }

    @Override
    public PostOfficesResponseDto getShippingAddress(String cityName) {
        City city = getCityByName(cityName);

        Set<PostOfficeDto> ukrPostOffices = city.getUkrPostOffices().stream()
                .map(postOfficeMapper::ukrPostToDto)
                .collect(Collectors.toSet());

        Set<PostOfficeDto> novaPostOffices = city.getNovaPostOffices().stream()
                .map(postOfficeMapper::novaPosToDto)
                .collect(Collectors.toSet());

        return new PostOfficesResponseDto(ukrPostOffices, novaPostOffices);
    }

    private City getCityByName(String name) {
        return cityRepository.findByName(name).orElseThrow(() ->
                new EntityNotFoundException("Can't find city with name: " + name));
    }
}
