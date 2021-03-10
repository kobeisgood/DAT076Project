package com.ejwa.frontend.resources;


import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import javax.ws.rs.DELETE;
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
        
         int id;
                
        try{
            id = Integer.parseInt(tid);        
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
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
        
         if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
    
        if(!(type.equals("INCOME") || type.equals("EXPENSE") || type.equals("SAVINGS"))){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Wrong type."))
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
       
     // TODO BEGIN TRANSATCION
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
            
            // COMMIT TRANSACTION
            
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
    
    
    @PUT
    public Response updateTransaction(JSONObject json){
        
        // Maybe add ignore_monthly and budget
        String[] data = {"id", "description", "date", "amount", "type"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        
        String description, type, date;
        int transactionId, amount;
        
         try{
           transactionId = Integer.parseInt(json.getAsString("id"));
           amount = Integer.parseInt(json.getAsString("amount"));
           description = json.getAsString("description");
           date = json.getAsString("date");
           type = json.getAsString("type");
        }
        catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))                            
                    .build();
        }
         
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
         
        if(!(type.equals("INCOME") || type.equals("EXPENSE") || type.equals("SAVINGS"))){
           return Response
                   .status(Response.Status.BAD_REQUEST)
                   .entity(API.error("Wrong type."))
                   .build();
       }
        
       try {
           databaseTX.begin();
       
       }
       catch(Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Transaction error."))
                    .build();     
        }
       
       Transactions transaction = transactionsDAO.find(transactionId);
       
       if(transaction == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such transaction."))
                    .build();            
        }
        
        try {
        
        
            
            transaction.setAmount(amount);
            transaction.setDate(new Date());
            transaction.setType(type);
            transaction.setDescription(description);
           
            
            transactionsDAO.flush();
            transactionsDAO.refresh(transaction);
            
            databaseTX.commit();
          

            return Response
                    .status(Response.Status.OK)
                    .entity(transactionsDAO.find(transactionId))
                    .build();
        } catch (Exception e) { // should not happen
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Server error."))
                    .build();
        }
       
    }
    
    @DELETE
    @Path("{tid")
    public Response deleteTransaction(@PathParam("tid") String tid) {
        
        int id;
        
        try{
            id = Integer.parseInt(tid);
        
        }catch(NumberFormatException e){
             return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        Transactions transaction = transactionsDAO.find(id);
        
       try {
           databaseTX.begin();
       
       }
       catch(Exception e){
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Transaction error."))
                    .build();     
        }
       
        if(transaction == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such transaction."))
                    .build();            
        }
        
        try {
        
        
            
           transactionsDAO.remove(transaction);
           
            
            transactionsDAO.flush();
            transactionsDAO.refresh(transaction);
            
            databaseTX.commit();
          

            return Response
                    .status(Response.Status.OK)
                    .entity(API.message("Transaction success"))
                    .build();
        } catch (Exception e) { // should not happen
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(API.error("Server error."))
                    .build();
        }
        
     
        
    }
}
