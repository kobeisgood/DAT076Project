package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Users;
import com.marcusaxelsson.lab3.model.entity.Budget;
import com.marcusaxelsson.lab3.model.entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class BudgetDAO extends AbstractDAO<Budget,String> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public BudgetDAO() {
                    super(Budget.class);
}
 }