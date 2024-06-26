package com.application.winelibrary.mapper;

import com.application.winelibrary.config.MapperConfig;
import com.application.winelibrary.dto.post.PostOfficeDto;
import com.application.winelibrary.entity.NovaPostOffice;
import com.application.winelibrary.entity.UkrPostOffice;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface PostOfficeMapper {
    PostOfficeDto ukrPostToDto(UkrPostOffice ukrPostOffice);

    PostOfficeDto novaPosToDto(NovaPostOffice novaPostOffice);
}
