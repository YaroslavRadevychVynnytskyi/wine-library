package com.application.winelibrary.repository.wine.provider;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SweetnessSpecificationProvider implements SpecificationProvider<Wine, Wine.Sweetness> {
    @Override
    public String getKey() {
        return "sweetness";
    }

    public Specification<Wine> getSpecification(Wine.Sweetness[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("sweetness").in(Arrays.stream(params).toArray());
    }
}
