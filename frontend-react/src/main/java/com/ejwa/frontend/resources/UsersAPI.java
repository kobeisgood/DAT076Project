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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;
import org.eclipse.persistence.exceptions.DatabaseException;

@Path("users")
public class UsersAPI {
    
    @EJB
    private UsersDAO usersDAO;
    
    @GET
    public List<Users> getAllUsers() {
        return usersDAO.findAll();
    }
    
    @GET
    @Path("{uid}")
    public Response getUserById(@PathParam("uid") String uid) throws IOException  {
        int id = Integer.parseInt(uid);
        
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
                    .entity("Wrong mail.")
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
        
        
        if(usersDAO.find(id) != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(usersDAO.findAllTransactions(id,y,m))
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
        
        int id = Integer.parseInt(uid);
        
        if(usersDAO.find(id) != null){
            
            Map<String,List<String>> lables = new HashMap<String, List<String>>();
            Map<String,List<String>> colors = new HashMap<String, List<String>>();
            Map<String,List<Integer>> data = new HashMap<String, List<Integer>>();
            Map<String,Map<String,Integer>> summary = new HashMap<String, Map<String,Integer>>();
            List<Object[]> rows = usersDAO.getDashboardInfo(id);
            
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
                
                lables.get(month).add(label);
                colors.get(month).add(color);
                data.get(month).add(Integer.parseInt(amount));
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
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("[]")
                    .build();
        }
        
    }
    
    @POST
    public Response newUser(JSONObject json) {
        
        String[] data = {"mail","password"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        try {
            Users newUser = new Users(json.getAsString("mail"),json.getAsString("password"));
            usersDAO.create(newUser);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(newUser)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(API.error("Duplicate mail."))
                    .build();
        }
        
    }
    
}
