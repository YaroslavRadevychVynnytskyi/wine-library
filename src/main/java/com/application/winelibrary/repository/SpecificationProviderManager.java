package com.application.winelibrary.repository;

public interface SpecificationProviderManager<T, R> {
    SpecificationProvider<T, R> getSpecificationProvider(String key);
}
