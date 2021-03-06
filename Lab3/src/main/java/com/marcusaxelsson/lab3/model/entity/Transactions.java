/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcusaxelsson.lab3.model.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author jjaok
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Transactions implements Serializable {

    @PrePersist
    private void prePersist(){
        date = new Date(System.currentTimeMillis());
        ignore_monthly = false;
    }

    @Id @GeneratedValue
    private int transactionId;

    @NonNull
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP) 
    private Date date;   
    
    @NonNull
    private int amount;
    
    @NonNull
    private String type; // INCOME, SAVINGS, EXPENSE
    
    private boolean ignore_monthly;
    
    @NonNull @ManyToOne 
    private Category category;
    
    @ManyToOne
    private Budget budget;
   
    public int getAmount(){
        if(type.equals("EXPENSE")){
            return -amount;            
        }
        else if(type.equals("SAVING")){
            return amount;  
        }
        else{ // INCOME
            return amount;
        }
    }
    
    
   
    
}
