package com.application.winelibrary.repository.custom;

import com.application.winelibrary.exception.InvalidJpqlException;
import java.util.List;

public interface CustomRepository<T> {
    List<T> findAllByJpqlQuery(String jpql, Class<T> entityClass) throws InvalidJpqlException;
}
