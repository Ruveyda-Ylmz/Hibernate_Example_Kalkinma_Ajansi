package com.ruveyda.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ICrud <T,ID> { //generic
    T save(T entity); //
    T update(T entity);
    Iterable<T>saveAll(Iterable<T>entities);

    void delete(T entity);
    void deleteByID(ID id);
    Optional<T> findById(ID id);

    Boolean existsById(ID id);

    List<T> findAll();

   List<T> findByColumnAndValue(String columnName, Objects value);

    List<T> findByColumnNameAndValue(String columnName, Object value);

    List<T> findAllEntity(T entity);

}
