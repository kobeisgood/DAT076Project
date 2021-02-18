package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Users;
import com.marcusaxelsson.lab3.model.entity.Category;
import com.marcusaxelsson.lab3.model.entity.CategoryPK;
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