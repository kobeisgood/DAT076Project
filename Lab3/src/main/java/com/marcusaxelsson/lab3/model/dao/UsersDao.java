package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lombok.Getter;

@Stateless
public class UsersDAO extends AbstractDAO<Users,Integer> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public UsersDAO() {
                    super(Users.class);
        }
  
       public Users findAccountByMail(String mail) {
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u "
                + "WHERE u.mail = :mail",Users.class);
        query.setParameter("mail", mail);
        return query.getSingleResult();
        
    }
       
        public List<Transactions> findAllTransactions(int userId) {
        TypedQuery<Transactions> query = entityManager.createQuery("SELECT t FROM Users u, Category c, Transactions t"
                +" WHERE c.categoryUser = u AND t.category = c AND u.id = :id"
                ,Transactions.class);
        
        
        query.setParameter("id", userId);
        return query.getResultList();
        
    }


}