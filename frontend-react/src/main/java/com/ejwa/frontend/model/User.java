/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejwa.frontend.model;

import com.ejwa.frontend.model.entity.Users;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import lombok.Data;

/**
 *
 * @author jjaok
 */
@Data
@SessionScoped
public class User implements Serializable{
    
    Users user;
        
    public int getId(){

       
         
                 
        return user.getId();
    }
    
}
