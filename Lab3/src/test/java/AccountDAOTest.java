
import com.marcusaxelsson.lab3.model.dao.*;
import com.marcusaxelsson.lab3.model.entity.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AccountDAOTest {
@Deployment
        public static WebArchive createDeployment() {
                return ShrinkWrap.create(WebArchive.class)
                    .addClasses(CategoryDAO.class, Category.class, UsersDAO.class, Users.class, BudgetDAO.class, Budget.class, TransactionDAO.class,Transactions.class, BudgetPK.class, CategoryPK.class)
                    .addAsResource("META-INF/persistence.xml")
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
}
    @EJB
    private CategoryDAO categoryDAO;
    @EJB
    private UsersDAO usersDAO;
    @EJB
    private BudgetDAO budgetDAO;
    @EJB
    private TransactionDAO transactionDAO;
    
    @Inject
    private UserTransaction databaseTX;
    
        
    @Test
    @InSequence(1)
    public void savingsTest() throws Exception{
        databaseTX.begin();
        
        
        Transactions t1 = new Transactions("Sparande",1000,"SAVINGS",false,categoryDAO.findAll().get(0));
        transactionDAO.create(t1);
        transactionDAO.flush();
        assertEquals(true,transactionDAO.contains(t1));
        usersDAO.refresh(usersDAO.find(1));
        int sum = 0;
        for( Transactions t : categoryDAO.findAll().get(0).getTransactions()){
            if(t.getType().equals("SAVINGS"))
                sum += t.getAmount();
        }
        assertEquals(1000,sum);
        
        
        databaseTX.commit();
    }
    
            

    @Test
    @InSequence(0)
    public void accountTest() throws Exception{
        
        databaseTX.begin();
        Users u1 = new Users("jjaokk@gmail.com","qwe123");
        usersDAO.create(u1); // Skapa i databasen
        usersDAO.flush(); // uppdatera databasen?
        assertEquals(true,usersDAO.contains(u1)); // Finns u1 i databasen?
        
            
       
        
        Category c1 = new Category("Mat",u1);
        Category c2 = new Category("Nöje",u1);
        Category c3 = new Category("Savings",u1);
        categoryDAO.create(c1);
        categoryDAO.create(c2); 
        categoryDAO.create(c3); 
        categoryDAO.flush();
        assertEquals(true,categoryDAO.contains(c1));
        assertEquals(true,categoryDAO.contains(c2));
       // u1 = usersDAO.find(u1.getId());
        usersDAO.refresh(u1);
        assertEquals(3,u1.getCategories().size());
        
                         
        Budget b1 = new Budget("Resa",u1);
        Budget b2 = new Budget("Husbygge",u1);
        budgetDAO.create(b1);
        budgetDAO.create(b2); 
        budgetDAO.flush();
        assertEquals(true,budgetDAO.contains(b1));
        assertEquals(true,budgetDAO.contains(b2));
        //u1 = usersDAO.find(u1.getId());
        usersDAO.refresh(u1);
        assertEquals(2,u1.getBudgets().size());
        
        
        Transactions t1 = new Transactions("Hemköp",100,"EXPENSE",false,c1);
        Transactions t2 = new Transactions("Hemköp",100,"EXPENSE",false,c2);
        transactionDAO.create(t1);
        transactionDAO.create(t2);
        transactionDAO.flush();
        assertEquals(true,transactionDAO.contains(t1));
        assertEquals(true,transactionDAO.contains(t2));
        usersDAO.refresh(u1);
        categoryDAO.refresh(c1);
        int sum = 0;
        for( Transactions t : u1.getCategories().get(0).getTransactions()){
            sum += t.getAmount();
        }
        assertEquals(-100,sum);
        
        databaseTX.commit();

        
        
        

    } 
    
    
}