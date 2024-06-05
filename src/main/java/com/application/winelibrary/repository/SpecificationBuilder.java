package com.application.winelibrary.repository;

import com.application.winelibrary.dto.wine.WineSearchParameters;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(WineSearchParameters searchParameters);
}
