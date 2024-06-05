package com.application.winelibrary.repository.wine;

import com.application.winelibrary.entity.Wine;
import com.application.winelibrary.repository.SpecificationProvider;
import com.application.winelibrary.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WineSpecificationProviderManager<R> implements SpecificationProviderManager<Wine, R> {
    private final List<SpecificationProvider<Wine, R>> wineSpecificationProviders;

    @Override
    public SpecificationProvider<Wine, R> getSpecificationProvider(String key) {
        return wineSpecificationProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find correct specification "
                        + "provider for key + " + key));
    }
}
