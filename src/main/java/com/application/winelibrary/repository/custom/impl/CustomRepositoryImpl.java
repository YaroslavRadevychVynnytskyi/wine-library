package com.application.winelibrary.repository.custom.impl;

import com.application.winelibrary.exception.InvalidJpqlException;
import com.application.winelibrary.repository.custom.CustomRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomRepositoryImpl<T> implements CustomRepository<T> {
    private final EntityManager entityManager;

    @Override
    public List<T> findAllByJpqlQuery(String jpql, Class<T> entityClass)
            throws InvalidJpqlException {
        isValidJpql(jpql);
        return entityManager.createQuery(jpql, entityClass).getResultList();
    }

    private void isValidJpql(String jpql) throws InvalidJpqlException {
        if (!jpql.toUpperCase().contains("SELECT")) {
            throw new InvalidJpqlException("Invalid query: " + jpql);
        }
    }
}
