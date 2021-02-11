package com.marcusaxelsson.lab3.model.dao;

import com.marcusaxelsson.lab3.model.entity.Car;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class CarDAO extends AbstractDAO<Car> {
       @Getter @PersistenceContext(unitName = "Lab3database")
       private EntityManager entityManager;
       public CarDAO() {
                    super(Car.class);
}
       public List<Car> findCarsMatchingName() {
                    throw new UnsupportedOperationException("Not yet implemented");
} }