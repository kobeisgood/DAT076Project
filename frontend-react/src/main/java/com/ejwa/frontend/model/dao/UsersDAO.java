package com.ejwa.frontend.model.dao;

import com.ejwa.frontend.model.entity.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lombok.Getter;
import net.minidev.json.JSONObject;

@Stateless
public class UsersDAO extends AbstractDAO<Users, Integer> {

    @Getter
    @PersistenceContext(unitName = "Lab3database")
    private EntityManager entityManager;

    public UsersDAO() {
        super(Users.class);
    }

    public Users findAccountByMail(String mail) {
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u "
                + "WHERE u.mail = :mail", Users.class);
        query.setParameter("mail", mail);

        List<Users> result = query.getResultList();
        if (result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public List<Transactions> findAllTransactions(int userId, int year, int month) {
        TypedQuery<Transactions> query = entityManager.createQuery("SELECT t FROM Users u, Category c, Transactions t"
                + " WHERE c.categoryUser = u AND t.category = c AND u.id = :id AND FUNC('YEAR',t.date) = :year AND FUNC('MONTH',t.date) = :month",
                 Transactions.class);

        query.setParameter("id", userId);
        query.setParameter("year", year);
        query.setParameter("month", month);

        return query.getResultList();
    }

    public List<Transactions> findAllTransactions(int userId, Date from, Date to) {
        TypedQuery<Transactions> query = entityManager.createQuery("SELECT t FROM Users u, Category c, Transactions t"
                + " WHERE c.categoryUser = u AND t.category = c AND u.id = :id AND t.date BETWEEN :from AND :to",
                 Transactions.class);

        query.setParameter("id", userId);
        query.setParameter("from", from);
        query.setParameter("to", to);

        return query.getResultList();
    }

    public List<Object[]> getDashboardInfo(int userId) {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT FUNC('YEAR',t.date) AS y, FUNC('MONTH',t.date) AS m, c.categoryName, c.color, t.amount, c.type FROM Users u, Category c, Transactions t"
                + " WHERE c.categoryUser = u AND t.category = c AND u.id = :id",
                 Object[].class);

        query.setParameter("id", userId);
        return query.getResultList();
    }

}
