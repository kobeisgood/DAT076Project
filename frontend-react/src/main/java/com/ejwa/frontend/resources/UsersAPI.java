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
    
    @GET
    @Path("{uid}/transactions")
    public Response getUserTransactions(@PathParam("uid") String uid) {
        
        int id = Integer.parseInt(uid);
        
        if(usersDAO.find(id) != null){
            return Response
                    .status(Response.Status.OK)
                    .entity(usersDAO.findAllTransactions(id))
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
                    .entity("{\"error\":\""+error+"\"}")
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
                    .entity("{\"error\":\"Duplicate mail\"}")
                    .build();
        }
        
    }
    
}
