
import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
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
import net.minidev.json.JSONObject;
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
    public void test() throws Exception{

      //  assertEquals("hej",usersDAO.findAllTransactions(1, 2021, 2));
   
       
      
      

    }
    
  
}