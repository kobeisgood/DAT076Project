package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Account;
import com.marcusaxelsson.lab3.model.entity.Car;
import com.marcusaxelsson.lab3.model.entity.Category;
import com.marcusaxelsson.lab3.model.entity.Transactions;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class TransactionDAO extends AbstractDAO<Transactions> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public TransactionDAO() {
                    super(Transactions.class);
}
       public List<Transactions> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} }