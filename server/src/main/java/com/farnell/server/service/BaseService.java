package com.farnell.server.service;



import com.farnell.server.model.BaseEntity;

import java.util.List;
import java.util.Set;

public interface BaseService<T extends BaseEntity> {
    T save(T BaseEntity);

    List<T> saveAll(List<T> BaseEntityList);

    T findById(Long id);

    List<T> findAllByIds(Set<Long> ids);

    List<T> findAll();

    void deleteById(Long id);

    T update(Long id, T BaseEntity);
}
