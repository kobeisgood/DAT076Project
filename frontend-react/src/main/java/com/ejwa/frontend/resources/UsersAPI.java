package com.ejwa.frontend.resources;

import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.Asset;

import org.jboss.shrinkwrap.api.spec.WebArchive;

@Path("users")
@ApplicationScoped
public class UsersAPI {
    
    
    
   
        
    @EJB
    private UsersDAO usersDAO;
    
    @Inject
        private UserTransaction databaseTX;

    
    
    @GET
    public List<Users> getAllUsers() {
        return usersDAO.findAll();
    }
    
    
    
    
    @GET
    @Path("{uid}")
    public Response getUserById(@PathParam("uid") String uid) throws IOException  {
        
        int id;
                
        try{
            id = Integer.parseInt(uid);
        
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        
        Users u = usersDAO.find(id);
        
        if(u != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(u)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("{}")
                    .build();
        }
    }
    
    
    @GET
    @Path("{uid}/categories")
    public Response getUserCategories(@PathParam("uid") String uid)  {
        
        int id;
                
        try{
            id = Integer.parseInt(uid);
        
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        
        Users u = usersDAO.find(id);
        
        if(u != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(u.getCategories())
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("{}")
                    .build();
        }
    }
    
    
    @POST
    @Path("login")
    public Response checkCredentials(JSONObject json) throws IOException  {
        String[] data = {"mail","password"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String mail = json.getAsString("mail");
        String password = json.getAsString("password");
 
        try {
            Users user = usersDAO.findAccountByMail(mail);
            if(user.getPassword().equals(password)){
                return Response
                    .status(Response.Status.OK)
                    .entity(user)
                    .build();                
            }else{
                return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Wrong password."))
                    .build(); 
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Wrong mail."))
                    .build();
        }
        
        
        
    }
    
    @GET
    @Path("{uid}/transactions/{year}/{month}")
    public Response getUserTransactions(@PathParam("uid") String uid, @PathParam("year") String year, @PathParam("month") String month) {
        
        int id,y,m;
        
        try{
            id = Integer.parseInt(uid);
            y = Integer.parseInt(year);
            m = Integer.parseInt(month);
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        List<Transactions> transactions = usersDAO.findAllTransactions(id,y,m);
        
        
        List<JSONObject> result = new ArrayList<>();
        
        
        Map<String,List<Transactions>> category = new HashMap<String, List<Transactions>>();

        for(Transactions transaction : transactions){
            String catName = transaction.getCategory().getCategoryName();
            
            category.putIfAbsent(catName, new ArrayList<Transactions>());
            
            category.get(catName).add(transaction);
            
            
            
        }

        for(String c : category.keySet()){
            JSONObject json = new JSONObject();
            json.appendField("name", c);
            json.appendField("data", category.get(c));
            
            result.add(json);
            
            
            
        }
        
        if(usersDAO.find(id) != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(result)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("[]")
                    .build();
        }
        
    }
    
    
    
    @GET
    @Path("{uid}/transactions/between/{from}/{to}")
    public Response getUserTransactionsBetween(@PathParam("uid") String uid, @PathParam("from") String from, @PathParam("to") String to) {
        
        int id;
        
       
        try{
            id = Integer.parseInt(uid);
           
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        List<Transactions> transactions = null;
        try{
         transactions = usersDAO.findAllTransactions(id,from,to);
        }
        catch(Exception e){
            return Response
                    .status(Response.Status.OK)
                    .entity(API.error("error"))
                    .build();
        }
        
        JSONObject result = new JSONObject();
        
        
        List<Integer> data = new ArrayList<Integer>();
        List<String> categories = new ArrayList<String>();
        List<String> colors = new ArrayList<String>();

        for(Transactions transaction : transactions){
            String catName = transaction.getCategory().getCategoryName();
            int amount = transaction.getAmount();
            
            if(!categories.contains(catName)){
                categories.add(catName);
            }
            
            int index = categories.indexOf(catName);
            
            
            if(data.size() == index){
                data.add(0);
            }
           
            data.set(index, data.get(index) + amount);
            
            if(colors.size() == index){
                colors.add(transaction.getCategory().getColor());
            }
           
           
            
            
            
        }

        
            result.appendField("data", data);
            result.appendField("lables", categories);
            result.appendField("colors", colors);
            
       
        
        if(usersDAO.find(id) != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(result)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("[]")
                    .build();
        }
        
    }
    
    
    
    
    @GET
    @Path("{uid}/dashboard")
    public Response getUserDashboard(@PathParam("uid") String uid) {
        
        int id;
                
        try{
            id = Integer.parseInt(uid);
        
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        if(usersDAO.find(id) != null){
            
            Map<String,List<String>> lables = new HashMap<String, List<String>>();
            Map<String,List<String>> colors = new HashMap<String, List<String>>();
            Map<String,List<Integer>> data = new HashMap<String, List<Integer>>();
            Map<String,Map<String,Integer>> summary = new HashMap<String, Map<String,Integer>>();
            List<Object[]> rows = usersDAO.getDashboardInfo(id);
            // year,month,categoryName,color,amount,type
            for(Object[] row : rows){
                String month = row[0].toString() + "-" + row[1].toString();
                String label = row[2].toString();
                String color = row[3].toString();
                String amount = row[4].toString();
                String type = row[5].toString();
                
                
                
                lables.putIfAbsent(month, new ArrayList<String>());
                colors.putIfAbsent(month, new ArrayList<String>());
                data.putIfAbsent(month, new ArrayList<Integer>());
                summary.putIfAbsent(month, new HashMap<String,Integer>());
                summary.get(month).putIfAbsent(type,0);
                
                if(!lables.get(month).contains(label))
                    lables.get(month).add(label);
                
                if(!colors.get(month).contains(color))
                    colors.get(month).add(color);
                
                int index = lables.get(month).indexOf(label);
                
                
                if(data.get(month).size() == index)
                    data.get(month).add(0);
                
                
                data.get(month).set(index, data.get(month).get(index) + Integer.parseInt(amount));
                
                summary.get(month).put(type,summary.get(month).get(type)+Integer.parseInt(amount));
                
            }
            
            List<JSONObject> result = new ArrayList<>();
            
            for (String m : lables.keySet()) {
                JSONObject json = new JSONObject();
                
                json.appendField("month",Integer.parseInt(m.substring(5)));
                json.appendField("year",Integer.parseInt(m.substring(0,4)));
                json.appendField("lables",lables.get(m));
                json.appendField("colors",colors.get(m));
                json.appendField("data",data.get(m));
                json.appendField("summary",summary.get(m));
                
                result.add(json);
     
            }
            
            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity(result)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("[]")
                    .build();
        }
        
    }
    
    
    
    @POST
    public Response newUser(JSONObject json) {
        
        String[] data = {"mail","password","firstName","lastName"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        try {
            Users newUser = new Users(json.getAsString("firstName"),json.getAsString("lastName"),json.getAsString("mail"),json.getAsString("password"));
            usersDAO.create(newUser);
            return Response
                    .status(Response.Status.OK)
                    .entity(newUser)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(API.error("Duplicate mail."))
                    .build();
        }
        
    }
    
    
    @PUT
    public Response updateUser(JSONObject json) {
        String[] data = {"id","mail","password","firstName","lastName"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String firstName,lastName,mail,password;
        int userId;
        
        try{
           userId = Integer.parseInt(json.getAsString("id"));
           firstName = json.getAsString("firstName");
           lastName = json.getAsString("lastName");
           mail = json.getAsString("mail");
           password = json.getAsString("password");
        }
        catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))                            
                    .build();
        }
                
        try{
            databaseTX.begin();
        }
        catch(Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Transaction error."))
                    .build();     
        }
        Users user = usersDAO.find(userId);
        
        

        if(user == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such user."))
                    .build();            
        }
        
        
       
        try {
        
        
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setMail(mail);
            user.setPassword(password);
            
            usersDAO.flush();
            usersDAO.refresh(user);
            
            databaseTX.commit();
          

            return Response
                    .status(Response.Status.OK)
                    .entity(usersDAO.find(userId))
                    .build();
        } catch (Exception e) { // should not happen
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Server error."))
                    .build();
        }
        
    }

    
}
