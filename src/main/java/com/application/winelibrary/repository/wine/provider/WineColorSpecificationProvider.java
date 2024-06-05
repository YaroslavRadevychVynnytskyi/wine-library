package com.application.winelibrary.repository.wine.provider;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class WineColorSpecificationProvider implements SpecificationProvider<Wine, Wine.WineType> {
    @Override
    public String getKey() {
        return "wineColor";
    }

    public Specification<Wine> getSpecification(Wine.WineType[] params) {
        return (root, query, criteriaBuilder) ->
                root.get("wineType").in(Arrays.stream(params).toArray());
    }
}
