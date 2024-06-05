package com.application.winelibrary.repository.wine;

import com.application.winelibrary.dto.wine.WineSearchParameters;
import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationBuilder;
import com.application.winelibrary.repository.SpecificationProviderManager;
import com.application.winelibrary.repository.wine.provider.PriceSpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WineSpecificationBuilder implements SpecificationBuilder<Wine> {
    private final SpecificationProviderManager<Wine, ? super Object> wineSpecProviderManager;
    private final PriceSpecificationProvider priceSpecProvider;

    @Override
    public Specification<Wine> build(WineSearchParameters searchParameters) {
        Specification<Wine> spec = Specification.where(null);
        if (searchParameters.minPrice() != null || searchParameters.maxPrice() != null) {
            spec = spec.and(priceSpecProvider
                    .getSpecification(searchParameters.minPrice(), searchParameters.maxPrice()));
        }
        if (searchParameters.colors() != null && searchParameters.colors().length > 0) {
            spec = spec.and(wineSpecProviderManager.getSpecificationProvider("wineColor")
                    .getSpecification(searchParameters.colors()));
        }
        if (searchParameters.sweetness() != null && searchParameters.sweetness().length > 0) {
            spec = spec.and(wineSpecProviderManager.getSpecificationProvider("sweetness")
                    .getSpecification(searchParameters.sweetness()));
        }
        if (searchParameters.countries() != null && searchParameters.countries().length > 0) {
            spec = spec.and(wineSpecProviderManager.getSpecificationProvider("country")
                    .getSpecification(searchParameters.countries()));
        }
        if (searchParameters.vintages() != null && searchParameters.vintages().length > 0) {
            spec = spec.and(wineSpecProviderManager.getSpecificationProvider("vintage")
                    .getSpecification(searchParameters.vintages()));
        }
        return spec;
    }
}
