package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Account;
import com.marcusaxelsson.lab3.model.entity.Budget;
import com.marcusaxelsson.lab3.model.entity.Car;
import com.marcusaxelsson.lab3.model.entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class BudgetDAO extends AbstractDAO<Budget> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public BudgetDAO() {
                    super(Budget.class);
}
       public List<Budget> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} }