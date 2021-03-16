
import com.ejwa.frontend.JAXRSConfiguration;
import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.*;
import com.ejwa.frontend.model.entity.*;
import com.ejwa.frontend.resources.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
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
                .addClasses(User.class,API.class,JAXRSConfiguration.class,UsersAPI.class,TransactionsAPI.class,CategoryAPI.class,CategoryDAO.class, Category.class, UsersDAO.class, Users.class, TransactionDAO.class,Transactions.class, CategoryPK.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @ArquillianResource
    private static URL deploymentURL;
    
    @EJB
    private CategoryDAO categoryDAO;
    
    @EJB
    private UsersDAO usersDAO;
    
    @EJB
    private TransactionDAO transactionDAO;
    
    static Client client;
    static int year,month,day;
    static String mail,firstName,lastName,password,JSESSIONID,currentDate,fromDate,toDate;
    static JSONObject JSONUser,JSONCategory1,JSONCategory2,JSONCategory3,JSONCategory4,JSONTransaction1,JSONTransaction2,
            JSONTransaction3,JSONTransaction4,JSONTransaction5,JSONTransaction6;
    
    static Users user;
    static Category c1,c2,c3,c4;
    
    @BeforeClass
    public static void before(){
        
        user = new Users();
        
        client = ClientBuilder.newClient();
        
        mail = "jjaokk@gmail.com";
        firstName = "Joakim";
        lastName = "Ohlsson";
        password = "qwe123";
        JSONUser = new JSONObject();
        
        year = 2021;
        month = 3;
        day = 16;
        
        currentDate = year+"-"+month+"-"+day;
        fromDate = year+"-"+month+"-"+(day-1);
        toDate = year+"-"+month+"-"+(day);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMail(mail);
        user.setPassword(password);
        user.setCategories(new ArrayList<Category>());
        
        JSONUser.appendField("password",password);
        JSONUser.appendField("firstName",firstName);
        JSONUser.appendField("lastName",lastName);
        
        JSONCategory1 = new JSONObject();
        JSONCategory2 = new JSONObject();
        JSONCategory3 = new JSONObject();
        JSONCategory4 = new JSONObject();
        
        JSONTransaction1 = new JSONObject();
        JSONTransaction2 = new JSONObject();
        JSONTransaction3 = new JSONObject();
        JSONTransaction4 = new JSONObject();
        JSONTransaction5 = new JSONObject();
        JSONTransaction6 = new JSONObject();
        
        JSONCategory1.appendField("categoryName", "Mat");
        JSONCategory1.appendField("color", "#000000");
        JSONCategory1.appendField("type", "EXPENSE");
        
        JSONCategory2.appendField("categoryName", "Hyra");
        JSONCategory2.appendField("color", "#abcdef");
        JSONCategory2.appendField("type", "EXPENSE");
        
        JSONCategory3.appendField("categoryName", "Lön");
        JSONCategory3.appendField("color", "#111111");
        JSONCategory3.appendField("type", "INCOME");
        
        JSONCategory4.appendField("categoryName", "Utlandsresa");
        JSONCategory4.appendField("color", "#222222");
        JSONCategory4.appendField("type", "SAVINGS");
        
        JSONTransaction1.appendField("amount",500);
        JSONTransaction1.appendField("description","Wyllis");
        JSONTransaction1.appendField("category",JSONCategory1.get("categoryName"));
        
        JSONTransaction2.appendField("amount",5000);
        JSONTransaction2.appendField("description","Hyra");
        JSONTransaction2.appendField("category",JSONCategory2.get("categoryName"));
        
        JSONTransaction3.appendField("amount",669);
        JSONTransaction3.appendField("description","Elräkning");
        JSONTransaction3.appendField("category",JSONCategory2.get("categoryName"));
        
        JSONTransaction4.appendField("amount",5000);
        JSONTransaction4.appendField("description","Till Resa");
        JSONTransaction4.appendField("category",JSONCategory4.get("categoryName"));
        
        JSONTransaction5.appendField("amount",25000);
        JSONTransaction5.appendField("description","Lön");
        JSONTransaction5.appendField("category",JSONCategory3.get("categoryName"));
        
        JSONTransaction6.appendField("amount",3000);
        JSONTransaction6.appendField("description","Aktier");
        JSONTransaction6.appendField("category",JSONCategory3.get("categoryName"));
        
        
    }
    
    
    @Test
    @InSequence(0)
    @RunAsClient
    public void getSessionIdTest() throws Exception{
        
        
        
        Response sessionIdResponse = client.target(deploymentURL + "api/users/session")
                .request(MediaType.TEXT_PLAIN)
                .get();
        
        JSESSIONID = sessionIdResponse.getHeaders().get("Set-Cookie").get(0).toString().substring(11,11+28);
        
        assertEquals(sessionIdResponse.getStatus(), 200);
        
    }
    
    @Test
    @InSequence(1)
    @RunAsClient
    public void createAccountTest() throws Exception{
        
        
        
        // Register with missing attribute (password)
        Response missingMailResponse = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        JSONUser.appendField("mail","test@asdasd");
        // Register with missing attribute (password)
        Response wrongMailFormat = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        
        // Register new user
        JSONUser.put("mail",mail);
        JSONObject newUserResponse = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(JSONUser, MediaType.APPLICATION_JSON),JSONObject.class);
        
        // Send same user info again (Duplicate mail)
        Response sameMailResponse = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        
        
        
        // Append generated id to user JSONObject
        JSONUser.appendField("id", newUserResponse.get("id"));
        user.setId(Integer.parseInt(newUserResponse.get("id").toString()));
        
        
        assertEquals(missingMailResponse.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(wrongMailFormat.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(sameMailResponse.getStatusInfo(), Response.Status.CONFLICT);
        assertEquals(newUserResponse,JSONUser);
        
    }
    
    @Test
    @InSequence(2)
    @RunAsClient
    public void loginTest() throws Exception{
        
        // Check session before login
        boolean notLoggedInBoolean = client.target(deploymentURL + "api/users/session")
                .request(MediaType.TEXT_PLAIN)
                .get(Boolean.class);
        
        JSONObject loginUser = new JSONObject();
        loginUser.appendField("mail",mail + "WRONG");
        
        // Login with missing attribute (password)
        Response missingPasswordResponse = client.target(deploymentURL + "api/users/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginUser, MediaType.APPLICATION_JSON));
        
        // Login with wrong mail
        loginUser.appendField("password",password);
        Response wrongMailResponse = client.target(deploymentURL + "api/users/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginUser, MediaType.APPLICATION_JSON));
        
        // Login with wrong password
        loginUser.replace("mail", mail); // correct mail
        loginUser.replace("password",password + "WRONG");
        Response wrongPasswordResponse = client.target(deploymentURL + "api/users/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(loginUser, MediaType.APPLICATION_JSON));
        
        // Login with correct credentials
        loginUser.replace("password", password);
        Response correctLoginResponse = client.target(deploymentURL + "api/users/login")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(loginUser, MediaType.APPLICATION_JSON));
        
        Users getUser = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get(Users.class);
        
        Response updateUserWrong = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        
        
        
        int id = JSONUser.getAsNumber("id").intValue();
        String mail = JSONUser.getAsString("mail");
        JSONUser.remove("id");
        
        JSONUser.put("mail","test");
        Response wrongMailFormat = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        JSONUser.put("mail",mail);

        
        Response updateUser = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        
        
        
        JSONUser.appendField("id", id);
        
        
        // Check session after login
        boolean loggedInBoolean = client.target(deploymentURL + "api/users/session")
                .request(MediaType.TEXT_PLAIN)
                .cookie("JSESSIONID",JSESSIONID)
                .get(Boolean.class);
        
        assertEquals(notLoggedInBoolean,false);
        assertEquals(missingPasswordResponse.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(wrongMailResponse.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(wrongPasswordResponse.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(updateUserWrong.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(updateUser.getStatusInfo(), Response.Status.OK);
        assertEquals(wrongMailFormat.getStatusInfo(), Response.Status.BAD_REQUEST);
        assertEquals(correctLoginResponse.getStatusInfo(), Response.Status.OK);
        
        assertEquals(getUser.getMail(), mail);
        assertEquals(loggedInBoolean,true);
        
    }
    
    @Test
    @InSequence(3)
    @RunAsClient
    public void addCategoriesTest() throws Exception{
        
        
        
        
        
        JSONCategory1.appendField("extra", "test");
        Response addCategoryWrongAruments = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON));
        JSONCategory1.remove("extra");
        
        String type = JSONCategory1.getAsString("type");
        JSONCategory1.put("type", "WRONG");
        Response addCategoryWrongType = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON));
        JSONCategory1.put("type", type);
        
        String color = JSONCategory1.getAsString("color");
        JSONCategory1.put("color", "#hexade");
        Response addCategoryWrongColor = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON));
        JSONCategory1.put("color", color);
        
        
        
        Category addCategory1 = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON), Category.class);
        
        Category addCategory2 = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory2, MediaType.APPLICATION_JSON), Category.class);
        
        Category addCategory3 = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory3, MediaType.APPLICATION_JSON), Category.class);
        
        Category addCategory4 = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON), Category.class);
        
        Response addCategory4Again = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        
        addCategory1.setCategoryUser(user);
        addCategory2.setCategoryUser(user);
        addCategory3.setCategoryUser(user);
        addCategory4.setCategoryUser(user);
        
        user.getCategories().add(addCategory1);
        user.getCategories().add(addCategory2);
        user.getCategories().add(addCategory3);
        user.getCategories().add(addCategory4);
        
        c1 = addCategory1;
        c2 = addCategory2;
        c3 = addCategory3;
        c4 = addCategory4;
        
        user.getCategories().add(c1);
        user.getCategories().add(c2);
        user.getCategories().add(c3);
        user.getCategories().add(c4);
        
        List<Category> getUserCategorys = client.target(deploymentURL + "api/users/categories")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get(List.class);
        
        
        
        assertEquals(addCategoryWrongAruments.getStatus(), 400);
        assertEquals(addCategoryWrongType.getStatus(), 400);
        assertEquals(addCategoryWrongColor.getStatus(), 400);
        assertEquals(addCategory4Again.getStatus(), 409);
        
        
        //TODO: fixa lämpliga asserts
        
    }
    @Test
    @InSequence(4)
    @RunAsClient
    public void updateCategoriesTest() throws Exception{
        
        
        
        JSONCategory4.appendField("test", "test");
        Response updateCategoryWrongArgs = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        JSONCategory4.remove("test");
        
        String color = JSONCategory4.getAsString("color");
        JSONCategory4.put("color", "test");
        Response updateCategoryWrongColor = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        JSONCategory4.put("color",color);
        
        String type = JSONCategory4.getAsString("type");
        JSONCategory4.put("type", "test");
        Response updateCategoryWrongType = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        JSONCategory4.put("type",type);
        
        
        
        String categoryName = JSONCategory4.getAsString("categoryName");
        JSONCategory4.put("categoryName", "categoryName");
        Response updateCategoryWrongName = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        JSONCategory4.put("categoryName",categoryName);
        
        
        
        Response updateCategory = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory4, MediaType.APPLICATION_JSON));
        
        
        
        assertEquals(updateCategoryWrongArgs.getStatus(), 400);
        assertEquals(updateCategoryWrongColor.getStatus(), 400);
        assertEquals(updateCategoryWrongType.getStatus(), 400);
        assertEquals(updateCategoryWrongName.getStatus(), 400);
        assertEquals(updateCategory.getStatus(), 200);
        
    }
    
    
    @Test
    @InSequence(5)
    @RunAsClient
    public void addTransactionsTest() {
        
        
        
        
        
        JSONTransaction1.put("test", "test");
        Response addTransactionWrongArgs = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction1, MediaType.APPLICATION_JSON));
        JSONTransaction1.remove("test");
        
        
        
        
        
        
        int amount = JSONTransaction1.getAsNumber("amount").intValue();
        JSONTransaction1.put("amount", "wrong");
        Response addTransactionWrongAmount = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction1, MediaType.APPLICATION_JSON));
        JSONTransaction1.put("amount", amount);
        
        JSONTransaction1.put("date", "wrong");
        Response addTransactionWrongDate = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction1, MediaType.APPLICATION_JSON));
        JSONTransaction1.put("date", fromDate);
        
        
        JSONTransaction1.put("category", "test");
        Response addTransactionWrongCategory = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction1, MediaType.APPLICATION_JSON));
        JSONTransaction1.put("category", JSONCategory1.get("categoryName"));
        
        
        Transactions addTransaction1 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction1, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction1.appendField("tid", addTransaction1.getTransactionId());
        
        
        
        
        
        
        Transactions addTransaction2 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction2, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction2.appendField("tid", addTransaction2.getTransactionId());
        
        Transactions addTransaction3 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction3, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction3.appendField("tid", addTransaction3.getTransactionId());
        
        Transactions addTransaction4 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction4, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction4.appendField("tid", addTransaction4.getTransactionId());
        
        Transactions addTransaction5 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction5, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction5.appendField("tid", addTransaction5.getTransactionId());
        
        Transactions addTransaction6 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON), Transactions.class);
        JSONTransaction6.appendField("tid", addTransaction6.getTransactionId());
        
        
        assertEquals(addTransactionWrongDate.getStatus(), 400);
        assertEquals(addTransactionWrongCategory.getStatus(), 400);
        assertEquals(addTransactionWrongAmount.getStatus(), 400);
        assertEquals(addTransactionWrongArgs.getStatus(), 400);
        
        
    }
    
    @Test
    @InSequence(6)
    @RunAsClient
    public void transactionUpdateTest() {
        
        Response updateTransactionWrongArguments = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        JSONTransaction6.put("amount", "test");
        JSONTransaction6.put("date", currentDate);
        JSONTransaction6.remove("category");
        
        Response updateTransactionWrongAmount = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        JSONTransaction6.put("amount", 5000);
        int tid = JSONTransaction6.getAsNumber("tid").intValue();
        JSONTransaction6.put("tid", 123456);
        
        Response updateTransactionWrongId = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        JSONTransaction6.put("tid", tid);
        
        
        Response updateTransaction6 = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        
        
        
        assertEquals(updateTransactionWrongArguments.getStatus(), 400);
        assertEquals(updateTransactionWrongAmount.getStatus(), 400);
        assertEquals(updateTransactionWrongId.getStatus(), 400);
        assertEquals(updateTransaction6.getStatus(), 200);
        
    }
    
    @Test
    @InSequence(7)
    @RunAsClient
    public void transactionDeleteTest() {
        
        Response deleteTransactionWrong = client.target(deploymentURL + "api/transactions/asd")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .delete();
        
        
        Response deleteTransaction6 = client.target(deploymentURL + "api/transactions/" + JSONTransaction6.getAsString("tid"))
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .delete();
        
        Response deleteTransactionNotExists = client.target(deploymentURL + "api/transactions/123456")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .delete();
        
        assertEquals(deleteTransactionWrong.getStatus(), 400);
        assertEquals(deleteTransactionNotExists.getStatus(), 400);
        assertEquals(deleteTransaction6.getStatus(), 200);
        
    }
    @Test
    @InSequence(8)
    @RunAsClient
    public void dashboardTest() {
        
        List<JSONObject> dashboardInfo = client.target(deploymentURL + "api/users/dashboard")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get(List.class);
        
        HashMap<String,BigDecimal> summary =((HashMap<String,BigDecimal>) (new JSONObject(dashboardInfo.get(0)).get("summary")));
        
        assertEquals(summary.get("INCOME"), new BigDecimal(25000));
        assertEquals(summary.get("SAVINGS"), new BigDecimal(5000));
        assertEquals(summary.get("EXPENSE"), new BigDecimal(6169));
        
    }
    
    
    @Test
    @InSequence(9)
    @RunAsClient
    public void monthlyTest() {
        
        Response transactionsMonthWrong = client.target(deploymentURL + "api/users/transactions/asd/asd")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        
        Response transactionsMonth = client.target(deploymentURL + "api/users/transactions/"+year+"/"+month)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        assertEquals(transactionsMonthWrong.getStatus(),400);
        assertEquals(transactionsMonth.getStatus(),200);
        
    }
    
    
    
    @Test
    @InSequence(10)
    @RunAsClient
    public void graphTest() {
        
        Response transactionsGraphWrong = client.target(deploymentURL + "api/users/transactions/between/asd/asd")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        
        Response transactionsGraph = client.target(deploymentURL + "api/users/transactions/between/"+fromDate+"/"+toDate)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        assertEquals(transactionsGraphWrong.getStatus(),400);
        assertEquals(transactionsGraph.getStatus(),200);
        
    }
    
    
    
    @Test
    @InSequence(20)
    @RunAsClient
    public void logoutTest() {
        
        Response logoutResponse = client.target(deploymentURL + "api/users/logout")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        Response logoutAgainResponse = client.target(deploymentURL + "api/users/logout")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        
        // TODO: ADD ALL CALLS FORBIDDEN
        
        
        Response deleteTransactionForbidden = client.target(deploymentURL + "api/transactions/1")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .delete();
        
        Response addTransactionForbidden = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        Response updateTransactionForbidden = client.target(deploymentURL + "api/transactions")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONTransaction6, MediaType.APPLICATION_JSON));
        
        Response addCategoryForbidden = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .post(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON));
        
        Response updateCategoryForbidden = client.target(deploymentURL + "api/category")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONCategory1, MediaType.APPLICATION_JSON));
        
        
        
        
        Response getCategoryForbidden = client.target(deploymentURL + "api/users/categories")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        Response getUserForbidden = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        Response updateUserForbidden = client.target(deploymentURL + "api/users")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .put(Entity.entity(JSONUser, MediaType.APPLICATION_JSON));
        
        Response getDashboardForbidden = client.target(deploymentURL + "api/users/dashboard")
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        
        Response transactionsGraphForbidden = client.target(deploymentURL + "api/users/transactions/between/"+fromDate+"/"+toDate)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        Response transactionsMonthForbidden = client.target(deploymentURL + "api/users/transactions/" + year + "/" + month)
                .request(MediaType.APPLICATION_JSON)
                .cookie("JSESSIONID",JSESSIONID)
                .get();
        
        assertEquals(logoutResponse.getStatus(), 200);
        assertEquals(logoutAgainResponse.getStatus(), 400);
        
        // FORBIDDEN
        assertEquals(deleteTransactionForbidden.getStatus(), 403);
        assertEquals(addTransactionForbidden.getStatus(), 403);
        assertEquals(updateTransactionForbidden.getStatus(), 403);
        assertEquals(addCategoryForbidden.getStatus(), 403);
        assertEquals(updateCategoryForbidden.getStatus(), 403);
        assertEquals(getCategoryForbidden.getStatus(), 403);
        assertEquals(getUserForbidden.getStatus(), 403);
        assertEquals(transactionsMonthForbidden.getStatus(), 403);
        assertEquals(transactionsGraphForbidden.getStatus(), 403);
        assertEquals(updateUserForbidden.getStatus(), 403);
        assertEquals(getDashboardForbidden.getStatus(), 403);
        
        
    }
    
}