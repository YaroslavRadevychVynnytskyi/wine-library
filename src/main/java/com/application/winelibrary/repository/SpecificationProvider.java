package com.application.winelibrary.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T, R> {
    String getKey();

    Specification<T> getSpecification(R[] params);
}
