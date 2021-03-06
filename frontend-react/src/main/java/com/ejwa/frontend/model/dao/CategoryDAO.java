package com.ejwa.frontend.model.dao;

import com.ejwa.frontend.model.entity.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;

@Stateless
public class CategoryDAO extends AbstractDAO<Category,CategoryPK> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public CategoryDAO() {
                    super(Category.class);
        }



}