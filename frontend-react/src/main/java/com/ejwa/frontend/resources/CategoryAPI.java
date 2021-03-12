package com.ejwa.frontend.resources;

import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;
import org.eclipse.persistence.exceptions.DatabaseException;

@Path("category")
@RequestScoped
public class CategoryAPI {
    
    @EJB
    private CategoryDAO categoryDAO;
 
    @EJB
    private UsersDAO usersDAO;
    
    
    @Inject
    private UserTransaction databaseTX;

 
    @POST
    public Response addCategory(JSONObject json) throws IOException  {

        String[] data = {"user","categoryName","color","type"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String name,color,type;
        int userId;
        
        try{
           name = json.getAsString("categoryName");
           type = json.getAsString("type");
           userId = Integer.parseInt(json.getAsString("user"));
           color = json.getAsString("color");
             
        }
        catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))                            
                    .build();
        }
        
        Users user = usersDAO.find(userId);
        
        if(user == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such user."))
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
        
        
        
        try {
            Category newCategory = new Category(name, user, color,type);
            categoryDAO.create(newCategory);
            categoryDAO.flush();
            databaseTX.commit();
            
            return Response
                    .status(Response.Status.CREATED)
                    .entity(newCategory)
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(API.error("Duplicate category."))
                    .build();
        }
    }
    
    @PUT
    public Response updateCategory(JSONObject json) throws IOException  {

        String[] data = {"user","categoryName","color","type"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String name,color,type;
        int userId;
        
        try{
           name = json.getAsString("categoryName");
           type = json.getAsString("type");
           userId = Integer.parseInt(json.getAsString("user"));
           color = json.getAsString("color");
        }
        catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))                            
                    .build();
        }
        
     
        CategoryPK key = new CategoryPK(name, userId);
        
        try{
            databaseTX.begin();
        }
        catch(Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Transaction error."))
                    .build();     
        }
        Category category = categoryDAO.find(key);

        if(category == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such category."))
                    .build();            
        }
        
        try {
            category.setColor(color);
            categoryDAO.flush();
            categoryDAO.refresh(category);
            databaseTX.commit();

            return Response
                    .status(Response.Status.OK)
                    .entity(category)
                    .build();
        } catch (Exception e) { // SHould not happen
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Server error."))
                    .build();
        }
    }
    
    
}
