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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import net.minidev.json.JSONObject;
import org.eclipse.persistence.exceptions.DatabaseException;

@Path("budgets")
public class BudgetAPI {
    
    @EJB
    private BudgetDAO budgetDAO;
 
    @GET
    public Response getBudget(@QueryParam("user") String uid, @QueryParam("budget") String budgetName) throws IOException  {
        if (uid == null || budgetName == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(API.error("Bad arguments."))
                    .build();
        }
        
        int userId = Integer.parseInt(uid);
        
        BudgetPK key = new BudgetPK(budgetName,userId);
        
        Budget b = budgetDAO.find(key);
        
        if(b != null){
            System.out.println(b.getTransactions().isEmpty());
            System.out.println(b.getTransactions());
            
            
            return Response
                    .status(Response.Status.OK)
                    .entity(b)
                    .build();
        }
        else{
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(API.error("No such budget."))
                    .build();
        }
    }
    
    
}
