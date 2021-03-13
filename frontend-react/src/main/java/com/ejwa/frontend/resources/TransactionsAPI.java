package com.ejwa.frontend.resources;


import com.ejwa.frontend.model.User;
import com.ejwa.frontend.model.dao.*;
import com.ejwa.frontend.model.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;

@Path("transactions")
@RequestScoped
public class TransactionsAPI {
    
    @EJB
    private TransactionDAO transactionsDAO;
    
    @EJB
    private CategoryDAO categoryDAO;
    
    @Inject
    private User userSession;
    
    boolean hasNoUserSession(){
//        return userSession.getUser() == null;
        return true; // ONLY FOR TESTING IN VS CODE
    }
    
    @POST
    @Transactional
    public Response addTransaction(JSONObject json){
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int userId = userSession.getId();
        
        if(!json.containsKey("ignore_monthly")){
            json.appendField("ignore_monthly", false);
        }
        
        Date date;
        
        if(!json.containsKey("date")){
            json.appendField("date", "CURRENT_TIMESTAMP");
            date = null;
        }
        else{
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                date = formatter.parse(json.getAsString("date"));
            }
            catch(ParseException e){
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(API.error("Bad date format."))
                        .build();
            }
        }
        
        String[] data = {"description","amount","category","ignore_monthly","date"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        int amount;
        String categoryName;
        
        try{
            amount = Integer.parseInt(json.getAsString("amount"));
            categoryName = json.getAsString("category");
        }catch(NumberFormatException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        CategoryPK key = new CategoryPK(categoryName,userId);
        Category category = categoryDAO.find(key);
        
        if(category == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such category."))
                    .build();
        }
        
        Transactions newTransaction = new Transactions(json.getAsString("description"), amount,category);
        
        transactionsDAO.create(newTransaction);
        
        if(json.getAsString("ignore_monthly").equalsIgnoreCase("true")){
            newTransaction.setIgnore_monthly(true);
        }
        if(date != null){
            newTransaction.setDate(date);
        }
        
        return Response
                .status(Response.Status.CREATED)
                .entity(newTransaction)
                .build();
        
    }
    
    @PUT
    @Transactional
    public Response updateTransaction(JSONObject json){
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        String[] data = {"tid","description", "date", "amount"};
        
        String error = API.matchDataInput(data, json);
        
        if(!error.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(error))
                    .build();
        }
        
        String description, date;
        int transactionId, amount;
        
        try{
            transactionId = Integer.parseInt(json.getAsString("tid"));
            amount = Integer.parseInt(json.getAsString("amount"));
            description = json.getAsString("description");
            date = json.getAsString("date");
        }
        catch(NumberFormatException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        Transactions transaction = transactionsDAO.find(transactionId);
        
        if(transaction == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such transaction."))
                    .build();
        }
        
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        transaction.setDescription(description);
        
        return Response
                .status(Response.Status.OK)
                .entity(transaction)
                .build();
        
    }
    
    @DELETE
    @Path("{tid}")
    @Transactional
    public Response deleteTransaction(@PathParam("tid") String tid) {
        
        if(hasNoUserSession()){
            return Response
                    .status(Response.Status.FORBIDDEN)
                    .entity(API.error("No session."))
                    .build();
        }
        
        int transactionId;
        
        try{
            transactionId = Integer.parseInt(tid);
            
        }catch(NumberFormatException e){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error(e.getMessage()))
                    .build();
        }
        
        Transactions transaction =transactionsDAO.find(transactionId);
        
        if(transaction == null){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("No such transaction."))
                    .build();
        }
            
        transactionsDAO.remove(transaction);
        
        return Response
                .status(Response.Status.OK)
                .entity(API.message("Transaction removed"))
                .build();

    }
    
}
