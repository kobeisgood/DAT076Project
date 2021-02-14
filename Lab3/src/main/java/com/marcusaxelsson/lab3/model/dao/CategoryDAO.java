package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Account;
import com.marcusaxelsson.lab3.model.entity.Car;
import com.marcusaxelsson.lab3.model.entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;

@Stateless
public class CategoryDAO extends AbstractDAO<Category> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public CategoryDAO() {
                    super(Category.class);
}
       public List<Category> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} 

 public int sumCategory(String catName, Account user) {
        Query query = entityManager.createQuery("SELECT a FROM Account a");
        query.setParameter("mail", mail);
        return query.getResultList();
    }

}