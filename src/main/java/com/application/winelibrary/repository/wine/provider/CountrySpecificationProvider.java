package com.application.winelibrary.repository.wine.provider;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CountrySpecificationProvider implements SpecificationProvider<Wine, String> {
    @Override
    public String getKey() {
        return "country";
    }

    public Specification<Wine> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("country").in(Arrays.stream(params).toArray());
    }
}
