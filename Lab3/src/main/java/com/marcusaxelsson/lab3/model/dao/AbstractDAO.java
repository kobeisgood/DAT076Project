package com.marcusaxelsson.lab3.model.dao;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.EntityManager;
import java.util.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractDAO<T> {
    
       private final Class<T> entityType;
       protected abstract EntityManager getEntityManager();
       
       public long count() {
                final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
                final CriteriaQuery cq = builder.createQuery();
                final Root<T> rt = cq.from(entityType);
                cq.select(builder.count(rt));
                final Query q = getEntityManager().createQuery(cq);
                return ((Long) q.getSingleResult());
       }
       
        public void create(T entity) {
                getEntityManager().persist(entity);
        
        }
        
        public List<T> findAll() {
                final CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
                cq.select(cq.from(entityType));
                return getEntityManager().createQuery(cq).getResultList();
        }
        
        public void remove(T entity) {
                getEntityManager().remove(getEntityManager().merge(entity));        
        } 
    }