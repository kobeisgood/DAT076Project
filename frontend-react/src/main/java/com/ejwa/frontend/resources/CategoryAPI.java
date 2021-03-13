package com.ejwa.frontend.resources;

import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.*;
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
import javax.transaction.Transactional;
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
    private User userSession;
    
    boolean hasNoUserSession(){
//        return userSession.getUser() == null;
        return true; // ONLY FOR TESTING IN VS CODE
    }
    
    @POST
    @Transactional
    public Response addCategory(JSONObject json) throws IOException  {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int userId = userSession.getId();
        
        String[] data = {"categoryName","color","type"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String name,color,type;
        
        name = json.getAsString("categoryName");
        type = json.getAsString("type");
        color = json.getAsString("color");
        
        Users user = usersDAO.find(userId);
        
        try {
            Category newCategory = new Category(name, user, color,type);
            categoryDAO.create(newCategory);
            
            
            categoryDAO.flush();

            return Response
                    .status(Response.Status.OK)
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
    @Transactional
    public Response updateCategory(JSONObject json) throws IOException  {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int userId = userSession.getId();
        
        String[] data = {"categoryName","color","type"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String name,color,type;
        
        name = json.getAsString("categoryName");
        type = json.getAsString("type");
        color = json.getAsString("color");
        
        CategoryPK key = new CategoryPK(name, userId);
        
        Category category = categoryDAO.find(key);
        
        if(category == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such category."))
                    .build();
        }
        
        category.setColor(color);
        
        return Response
                .status(Response.Status.OK)
                .entity(category)
                .build();
        
    }
    
}
