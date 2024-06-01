package com.application.winelibrary.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.application.winelibrary.dto.food.FoodDto;
import com.application.winelibrary.dto.wine.CreateWineRequestDto;
import com.application.winelibrary.dto.wine.WineDetailedResponseDto;
import com.application.winelibrary.entity.Food;
import com.application.winelibrary.entity.Wine;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WineControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/wines_foods/remove-wines_foods-from-wines_foods-table.sql",
            "classpath:database/wines/remove-wines-from-wines-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Verify save() works properly when all ok")
    void save_AllOk_Success() throws Exception {
        //Given
        CreateWineRequestDto requestDto = new CreateWineRequestDto(
                "Bolgrad Costa Sur",
                "Bolgrad",
                "Ukraine",
                Year.of(2020),
                750,
                15,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );
        WineDetailedResponseDto expected = new WineDetailedResponseDto(
                41L,
                "Bolgrad Costa Sur",
                "Bolgrad",
                "Ukraine",
                Year.of(2020),
                750,
                15,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        //When
        MvcResult result = mockMvc.perform(
                post("/wines")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        WineDetailedResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), WineDetailedResponseDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @Sql(scripts = {
            "classpath:database/wines/add-mock-wine-to-wines-table.sql",
            "classpath:database/wines_foods/add-mock-wines_foods-to-wines_foods-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/wines_foods/remove-wines_foods-from-wines_foods-table.sql",
            "classpath:database/wines/remove-wines-from-wines-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @DisplayName("Verify findById() works fine when all ok")
    void findById_AllOk_Success() throws Exception {
        //Given
        WineDetailedResponseDto expected = new WineDetailedResponseDto(
                42L,
                "Bolgrad Costa Sur",
                "Bolgrad",
                "Ukraine",
                Year.of(2024),
                1000,
                14,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );

        //When
        MvcResult result = mockMvc.perform(
                get("/wines/41")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        WineDetailedResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), WineDetailedResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @Sql(scripts = {
            "classpath:database/wines/add-mock-wine-to-wines-table.sql",
            "classpath:database/wines_foods/add-mock-wines_foods-to-wines_foods-table.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/wines_foods/remove-wines_foods-from-wines_foods-table.sql",
            "classpath:database/wines/remove-wines-from-wines-table.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Verify updateById() works correctly when all ok")
    void updateById_AllOk_Success() throws Exception {
        //Given
        CreateWineRequestDto requestDto = new CreateWineRequestDto(
                "Updated Name",
                "Bolgrad",
                "Ukraine",
                Year.of(2024),
                1400,
                17,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        WineDetailedResponseDto expected = new WineDetailedResponseDto(
                41L,
                "Updated Name",
                "Bolgrad",
                "Ukraine",
                Year.of(2024),
                1400,
                17,
                Wine.WineType.RED,
                Set.of(new FoodDto(Food.FoodName.COLD_SNACKS.name())),
                Wine.Sweetness.DRY,
                Wine.Acidity.MEDIUM,
                "Good cheep wine",
                340,
                BigDecimal.valueOf(200),
                "https://content1.rozetka.com.ua/goods/images/big/174123270.jpg"
        );

        //When
        MvcResult result = mockMvc.perform(
                put("/wines/41")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        WineDetailedResponseDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), WineDetailedResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }
}
