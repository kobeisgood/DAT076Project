package com.ejwa.frontend.resources;

import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jboss.shrinkwrap.api.spec.WebArchive;

@Path("users")
@RequestScoped
public class UsersAPI {
    
    @Inject
    private User userSession;
    
    @EJB
    private UsersDAO usersDAO;

    
    
    
    boolean hasNoUserSession(){
        return userSession.getUser() == null;
        // return false; // ONLY FOR TESTING IN VS CODE
       
    }
    
    @GET
    @Path("/session")
    public Response isLoggedIn(){
        
        
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.OK)
                    .entity(false)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.OK)
                    .entity(true)
                    .build();
        }
    }
    
    @GET
    public Response getUser() throws IOException  {
        
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.OK)
                    .entity(usersDAO.find(userSession.getId()))
                    .build();
        }
        
    }
    
    @GET
    @Path("/categories")
    @Transactional
    public Response getUserCategories(@PathParam("uid") String uid)  {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        Users u = usersDAO.find(userSession.getId());
        
        usersDAO.refresh(u);
        
        return Response
                .status(Response.Status.OK)
                .entity(u.getCategories())
                .build();
        
    }
    
    @POST
    @Path("login")
    @Transactional
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
                
                userSession.setUser(user);
                
                return Response
                        .status(Response.Status.OK)
                        .entity(userSession.getUser())
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
    @Path("logout")
    public Response logout() throws IOException  {
        
        if(hasNoUserSession()){
            
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Not logged in."))
                    .build();
        }
        else{
            
            userSession.setUser(null);
            
            return Response
                    .status(Response.Status.OK)
                    .entity(API.message("Success."))
                    .build();
        }
        
    }
    
    @GET
    @Path("/transactions/{year}/{month}")
    @Transactional
    public Response getUserTransactions(@PathParam("year") String year, @PathParam("month") String month) {
        
        
        if(hasNoUserSession()){
            
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int id = userSession.getId();
        
        int y,m;
        
        try{
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
        
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
        
    }
    
    @GET
    @Path("/transactions/between/{from}/{to}")
    @Transactional
    public Response getUserTransactionsBetween(@PathParam("from") String from, @PathParam("to") String to) {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int id = userSession.getId();
        
        Date f,t;
        
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            f = formatter.parse(from);
            t = formatter.parse(to);
        }
        catch(ParseException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Wrong date."))
                    .build();
        }
        
        List<Transactions> transactions = usersDAO.findAllTransactions(id,f,t);
        
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
        
        return Response
                .status(Response.Status.OK)
                .entity(result)
                .build();
        
    }
    
    @GET
    @Path("/dashboard")
    @Transactional
    public Response getUserDashboard() {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(new API().error("No session."))
                    .build();
        }
        
        int id = userSession.getId();
        
        Map<String,List<String>> lables = new HashMap<String, List<String>>();
        Map<String,List<String>> colors = new HashMap<String, List<String>>();
        Map<String,List<Integer>> data = new HashMap<String, List<Integer>>();
        Map<String,Map<String,Integer>> summary = new HashMap<String, Map<String,Integer>>();
        List<Object[]> rows = usersDAO.getDashboardInfo(id);
        
        // row = [year,month,categoryName,color,amount,type]
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
                .status(Response.Status.OK)
                .entity(result)
                .build();
        
    }
    
    @POST
    @Transactional
    public Response newUser(JSONObject json) {
        
        String[] data = {"mail","password","firstName","lastName"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String mailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        
        if(!json.getAsString("mail").matches(mailRegex)){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Not a valid mail."))
                    .build();
        }
        
        
        if(usersDAO.findAccountByMail(json.getAsString("mail")) != null){
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(API.error("Duplicate mail."))
                    .build();
        }
        
        
        Users newUser = new Users(json.getAsString("mail"),json.getAsString("firstName"),json.getAsString("lastName"),json.getAsString("password"));
        
        usersDAO.create(newUser);
        
        return Response
                .status(Response.Status.OK)
                .entity(newUser)
                .build();
        
        
        
    }
    
    @PUT
    @Transactional
    public Response updateUser(JSONObject json) {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int userId = userSession.getId();
        
        String[] data = {"mail","password","firstName","lastName"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String firstName,lastName,mail,password;
        
        firstName = json.getAsString("firstName");
        lastName = json.getAsString("lastName");
        mail = json.getAsString("mail");
        password = json.getAsString("password");
        
        
        
        String mailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        
        if(!mail.matches(mailRegex)){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Not a valid mail."))
                    .build();
        }
        
        Users user = usersDAO.find(userId);
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMail(mail);
        user.setPassword(password);
        
        usersDAO.flush();
        usersDAO.refresh(user);
        
        return Response
                .status(Response.Status.OK)
                .entity(usersDAO.find(userId))
                .build();
        
    }
    
}
