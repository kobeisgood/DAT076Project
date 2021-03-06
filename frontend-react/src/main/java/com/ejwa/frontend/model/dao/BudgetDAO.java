package com.ejwa.frontend.model.dao;

import com.ejwa.frontend.model.entity.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class BudgetDAO extends AbstractDAO<Budget,BudgetPK> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public BudgetDAO() {
                    super(Budget.class);
    }
}