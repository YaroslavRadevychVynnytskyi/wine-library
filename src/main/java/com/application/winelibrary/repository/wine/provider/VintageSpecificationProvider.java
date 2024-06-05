package com.application.winelibrary.repository.wine.provider;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationProvider;
import java.time.Year;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class VintageSpecificationProvider implements SpecificationProvider<Wine, Year> {
    @Override
    public String getKey() {
        return "vintage";
    }

    public Specification<Wine> getSpecification(Year[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("year").in(Arrays.stream(params).toArray());
    }
}
