package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Account;
import com.marcusaxelsson.lab3.model.entity.Car;
import com.marcusaxelsson.lab3.model.entity.Category;
import com.marcusaxelsson.lab3.model.entity.Transaction;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class TransactionDAO extends AbstractDAO<Transaction> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public TransactionDAO() {
                    super(Transaction.class);
}
       public List<Transaction> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} }