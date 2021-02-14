/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcusaxelsson.lab3.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author jjaok
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transactions implements Serializable {

   

    @Id
    private String transactionId;

    private String description;
    private String datee;    
    private int amount;
    
    private String type;
    private boolean ignore_monthly;
    
    @ManyToOne
    private Category category;
    
    @ManyToOne
    private Budget budget;
   
    
    
   
    
}
