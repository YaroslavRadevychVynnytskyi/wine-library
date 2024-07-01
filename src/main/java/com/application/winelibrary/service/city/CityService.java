package com.application.winelibrary.service.city;

import com.application.winelibrary.dto.post.PostOfficesResponseDto;
import java.util.List;

public interface CityService {
    List<String> getCities();

    PostOfficesResponseDto getShippingAddress(String cityName);
}
