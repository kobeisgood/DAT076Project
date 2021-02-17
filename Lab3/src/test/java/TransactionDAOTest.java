
import com.marcusaxelsson.lab3.model.dao.*;
import com.marcusaxelsson.lab3.model.entity.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
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
public class TransactionDAOTest {
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
    private UsersDAO accountDAO;
    @EJB
    private BudgetDAO budgetDAO;
    @EJB
    private TransactionDAO transactionDAO;
    
    List<Transactions> transactions = new ArrayList<Transactions>();
    List<Category> categories = new ArrayList<Category>();
    List<Budget> budgets = new ArrayList<Budget>();
    List<Users> accounts = new ArrayList<Users>();
    
    @Before
    public void init() {
                Users acc1 = new Users("1", "jjaokk@gmail.com", "qwe123");
                accounts.add(acc1);
                for (Users a : accounts) {
                    accountDAO.create(a);
                }
                
                
                Category c1 = new Category("Mat",acc1);
                Category c2 = new Category("Nöje",acc1);
                Category c3 = new Category("Hyra",acc1);
                Category c4 = new Category("Lön",acc1);
                Category c5 = new Category("Sparande",acc1);
                categories.add(c1);
                categories.add(c2);
                categories.add(c3);
                categories.add(c4);
                categories.add(c5);
                for (Category c : categories) {
                    categoryDAO.create(c);
                }
                
                Budget b1 = new Budget("Resa", acc1);
                Budget b2 = new Budget("Husbygge", acc1);
                budgets.add(b1);
                budgets.add(b2);
                for (Budget b : budgets) {
                    budgetDAO.create(b);
                }

                Transactions t1 = new Transactions("t1","Hemköp","2021-02-14",100,"EXPENSE",false,c1,null);
                Transactions t2 = new Transactions("t2","Bio","2021-02-14",220,"EXPENSE",false,c2,null);
                Transactions t3 = new Transactions("t3","Ström","2021-02-14",223,"EXPENSE",false,c3,null);
                Transactions t4 = new Transactions("t4","Ica storhandla","2021-02-13",1000,"EXPENSE",true,c1,b2);
                Transactions t5 = new Transactions("t5","Lön","2021-02-13",10000,"INCOME",true,c4,null);
                Transactions t6 = new Transactions("t6","Till resa","2021-02-13",15000,"SAVING",true,c5,b1);
                transactions.add(t1);
                transactions.add(t2);
                transactions.add(t3);
                transactions.add(t4);
                transactions.add(t5);
                transactions.add(t6);
                for (Transactions t : transactions) {
                    transactionDAO.create(t);
                }
                        
    }
    
   
   

    @Test
    public void transactionTest() {
        assertEquals(transactionDAO.count(),6);

       int sum = 0;
        for(Transactions t : transactionDAO.findAll()){
            sum += t.getAmount();   
        }
        assertEquals(sum,8457); 

    }
    
    @After
    public void remove(){
        for (Transactions t : transactions) {
            transactionDAO.remove(t);
        }
        for (Budget b : budgets) {
            budgetDAO.remove(b);
        }
        for (Category c : categories) {
            categoryDAO.remove(c);
        }
        for (Users a : accounts) {
            accountDAO.remove(a);
        }
        
    }

}