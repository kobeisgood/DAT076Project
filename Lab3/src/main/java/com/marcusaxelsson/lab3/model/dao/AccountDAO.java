package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Account;
import com.marcusaxelsson.lab3.model.entity.Car;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class AccountDAO extends AbstractDAO<Account> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public AccountDAO() {
                    super(Account.class);
}
       public List<Account> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} }