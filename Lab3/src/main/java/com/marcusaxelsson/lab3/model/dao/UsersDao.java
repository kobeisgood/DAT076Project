package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.Getter;

@Stateless
public class UsersDAO extends AbstractDAO<Users,Integer> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public UsersDAO() {
                    super(Users.class);
}
  
      
       
       public List<Users> findAccountByMail(String mail) {
           
           
           
           Query query = entityManager.createQuery("SELECT a FROM Users a "
                + "WHERE a.mail = :mail");
        query.setParameter("mail", mail);
        return query.getResultList();
        
    }


}