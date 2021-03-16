package com.ejwa.frontend.model.dao;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.EntityManager;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractDAO<T, K> {

    private final Class<T> entityType;

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void refresh(T entity) {
        getEntityManager().refresh(entity);
    }

    public void flush() {
        getEntityManager().flush();
    }

    public T find(K key) {
        return getEntityManager().find(entityType, key);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
