package com.ejwa.frontend.resources;


import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;

@Path("transactions")
@ApplicationScoped

public class TransactionsAPI {
    
    @EJB
    private TransactionDAO transactionsDAO;
    
    @EJB
    private CategoryDAO categoryDAO;
     
     @Inject
        private UserTransaction databaseTX;

    
    @GET
    @Path("{tid}")
    public Response getTransactionById(@PathParam("tid") String tid) {
        
        int id = Integer.parseInt(tid);
        
        Transactions t = transactionsDAO.find(id);
        
        if(t == null){
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("{}")
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.OK)
                    .entity(t)
                    .build();
        }
        
    }
    
    @POST
    public Response add(JSONObject json){
                
        if(!json.containsKey("ignore_monthly")){
            json.appendField("ignore_monthly", false);
        }

        if(!json.containsKey("date") || json.get("date") == null){
            json.appendField("date", "CURRENT_TIMESTAMP");
        }
        
        String[] data = {"description","amount","type","category","user","ignore_monthly","date"};
        
        String error = API.matchDataInput(data, json);

        String type = json.getAsString("type");
        
    
        if(!(type.equals("INCOME") || type.equals("EXPENSE") || type.equals("SAVINGS"))){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Wrong type."))
                    .build();
        }

        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        
        int amount,userId;
        try{
            amount = Integer.parseInt(json.getAsString("amount"));
            userId = Integer.parseInt(json.getAsString("user"));
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
       

        CategoryPK key = new CategoryPK(json.getAsString("category"),userId);
        Category category = categoryDAO.find(key);
        
        if(category == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(key.toString()))
                    .build();
        }
                
        try {
            Transactions newTransaction = new Transactions(json.getAsString("description"), amount, json.getAsString("type"),category);
            transactionsDAO.create(newTransaction);
            if(json.getAsString("ignore_monthly").equalsIgnoreCase("true")){
                newTransaction.setIgnore_monthly(true);
            }
            if(!json.get("date").equals("CURRENT_TIMESTAMP")){
                // TODO: set date with correct format
            }
            return Response
                    .status(Response.Status.CREATED)
                    .entity(newTransaction)
                    .build();
        } catch (Exception e) { // Should not happen
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        
    }
    
}
