package com.ejwa.frontend.model.dao;

import com.ejwa.frontend.model.entity.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import lombok.Getter;

@Stateless
public class TransactionDAO extends AbstractDAO<Transactions, Integer> {

    @Getter
    @PersistenceContext(unitName = "Lab3database")
    private EntityManager entityManager;

    public TransactionDAO() {
        super(Transactions.class);
    }

}
