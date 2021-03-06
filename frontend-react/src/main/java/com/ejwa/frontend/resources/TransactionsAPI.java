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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("transactions")
public class TransactionsAPI {
    
    @EJB
    private TransactionDAO transactionsDAO;
     
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
    public Response add(Transactions transaction){
        
        if(transaction.getDescription() != null && transaction.getAmount() != 0 && transaction.getType() != null && transaction.getCategory() != null){
            
            try{
                transactionsDAO.create(transaction);
                return Response
                        .status(Response.Status.OK)
                        .entity(transaction)
                        .build();
            }
            catch(Exception e){
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("oops")
                        .build();
            } 
        }  
        else{
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("buuuuuuuuu")
                    .build();
        }
    }
    
}
