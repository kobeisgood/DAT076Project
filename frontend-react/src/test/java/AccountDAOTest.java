
import com.ejwa.frontend.JAXRSConfiguration;
import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.*;
import com.ejwa.frontend.model.entity.*;
import com.ejwa.frontend.resources.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
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
                    .addClasses(User.class,API.class,JAXRSConfiguration.class,UsersAPI.class,CategoryDAO.class, Category.class, UsersDAO.class, Users.class, BudgetDAO.class, Budget.class, TransactionDAO.class,Transactions.class, BudgetPK.class, CategoryPK.class)
                    .addAsResource("META-INF/persistence.xml")
                    .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
}
        @ArquillianResource
        private URL deploymentURL;
        
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
    @InSequence(0)
    @RunAsClient
    public void createAccountTest() throws Exception{
        

       // assertEquals(deploymentURL + "/api/users/isloggedin", "test");
        Client client = ClientBuilder.newClient();
       

        JSONObject user = new JSONObject();
        
        user.appendField("mail","jjaokk@gmail.com");
        user.appendField("firstName","Joakim");
        user.appendField("lastName","Ohlsson");
        user.appendField("password","qwe123");
        
        Response response = client.target(deploymentURL + "api/users/")
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(user, MediaType.APPLICATION_JSON));
        
        
        
        
        JSONObject json = client.target(deploymentURL + "api/users/1")
        .request(MediaType.APPLICATION_JSON)
        .get(JSONObject.class);
        
        //assertEquals(json,"Joakim");
        
        
    }
    
}
      /*  databaseTX.begin(); // begin Transaction
        
        // Create 1 Users
        Users u1 = new Users("jjaokk@gmail.com","Joakim","Ohlsson","qwe123"); 
        usersDAO.create(u1); // add to database
        usersDAO.flush(); // force the commit inside the Transacion
        
        // Entity exists in DAO
        assertEquals(true,usersDAO.contains(usersDAO.findAccountByMail("jjaokk@gmail.com")));
        
        // Equal entity in databse 
        assertEquals(true,u1.equals(usersDAO.findAccountByMail("jjaokk@gmail.com"))); 
        
        databaseTX.commit();// end Transaction
    }
    
    @Test
    @InSequence(1)
    public void createCategoriesTest() throws Exception{
        databaseTX.begin(); // begin Transaction
        
        // Create 2 Categories
        Category c1 = new Category("Mat",usersDAO.findAccountByMail("jjaokk@gmail.com"),"#ffffff");
        Category c2 = new Category("Nöje",usersDAO.findAccountByMail("jjaokk@gmail.com"),"#000000");
        categoryDAO.create(c1);
        categoryDAO.create(c2); 
        categoryDAO.flush();

        // Does entitys exists in DAO
        assertEquals(true,categoryDAO.contains(categoryDAO.find(new CategoryPK("Mat",usersDAO.findAccountByMail("jjaokk@gmail.com").getId()))));
        assertEquals(true,categoryDAO.contains(categoryDAO.find(new CategoryPK("Nöje",usersDAO.findAccountByMail("jjaokk@gmail.com").getId()))));
        
        // Does user has 2 categories
        usersDAO.refresh(usersDAO.findAccountByMail("jjaokk@gmail.com"));
        assertEquals(2,usersDAO.findAccountByMail("jjaokk@gmail.com").getCategories().size());
                
        databaseTX.commit();// end Transaction

    } 
    
    @Test
    @InSequence(2)
    public void createBudgetsTest() throws Exception{
        databaseTX.begin(); // begin Transaction

        // Create 2 Budgets
        Budget b1 = new Budget("Resa",usersDAO.findAccountByMail("jjaokk@gmail.com"));
        Budget b2 = new Budget("Husbygge",usersDAO.findAccountByMail("jjaokk@gmail.com"));
        budgetDAO.create(b1);
        budgetDAO.create(b2); 
        budgetDAO.flush();
        
        assertEquals(true,budgetDAO.contains(budgetDAO.find(new BudgetPK("Resa",usersDAO.findAccountByMail("jjaokk@gmail.com").getId()))));
        assertEquals(true,budgetDAO.contains(budgetDAO.find(new BudgetPK("Husbygge",usersDAO.findAccountByMail("jjaokk@gmail.com").getId()))));
        
        // Does user has 2 budgets
        usersDAO.refresh(usersDAO.findAccountByMail("jjaokk@gmail.com"));
        assertEquals(2,usersDAO.findAccountByMail("jjaokk@gmail.com").getBudgets().size());
        
        databaseTX.commit();// end Transaction
    }
    
    
    @Test
    @InSequence(3)
    public void createTransactionsTest() throws Exception{
        
        int currentMonth = 3;
        int currentYear = 2021;
        
        databaseTX.begin(); // begin Transaction

        Transactions t1 = new Transactions("Hemköp",100,"EXPENSE",categoryDAO.find(new CategoryPK("Mat",usersDAO.findAccountByMail("jjaokk@gmail.com").getId())));
        Transactions t2 = new Transactions("Bio",200,"EXPENSE",categoryDAO.find(new CategoryPK("Nöje",usersDAO.findAccountByMail("jjaokk@gmail.com").getId())));
        Transactions t3 = new Transactions("Sparande",5000,"SAVINGS",categoryDAO.find(new CategoryPK("Mat",usersDAO.findAccountByMail("jjaokk@gmail.com").getId())));
        t3.setIgnore_monthly(true);
        Transactions t4 = new Transactions("Borrmaskin",1599,"EXPENSE",categoryDAO.find(new CategoryPK("Mat",usersDAO.findAccountByMail("jjaokk@gmail.com").getId())));
        t4.setBudget(budgetDAO.find(new BudgetPK("Husbygge",usersDAO.findAccountByMail("jjaokk@gmail.com").getId())));
        t4.setIgnore_monthly(true);
        transactionDAO.create(t1);
        transactionDAO.create(t2);
        transactionDAO.create(t3);
        transactionDAO.create(t4);
        transactionDAO.flush();
        
        // Should be 4 Transactions in DAO
        assertEquals(4,transactionDAO.findAll().size());
        
        usersDAO.refresh(usersDAO.findAccountByMail("jjaokk@gmail.com"));
        int sum = 0;
        for(Transactions t : usersDAO.findAllTransactions(usersDAO.findAccountByMail("jjaokk@gmail.com").getId(),currentYear,currentMonth)){
            sum += t.getAmount();
        }
        assertEquals(-1899+5000,sum);
        
        databaseTX.commit();// end Transaction
    }
    
  
}
*/
    