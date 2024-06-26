package com.application.winelibrary.service.city.impl;

import com.application.winelibrary.service.city.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
@RequiredArgsConstructor
@Profile("!test")
public class GeoNameService implements CityService {
    @Value("${geonames.url}")
    private String getGeoNamesUrl;

    private final RestOperations restTemplate;
    private final ObjectMapper objectMapper;

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
}
