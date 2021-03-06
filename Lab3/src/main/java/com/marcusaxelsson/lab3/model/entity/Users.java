/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcusaxelsson.lab3.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class Users implements Serializable {

    @Id @GeneratedValue
    private int id;
    
    @Column(unique=true)
    @NonNull 
    private String mail;
    
    @NonNull
    private String password;

    @OneToMany(mappedBy = "budgetUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Budget> budgets;
    
    @OneToMany(mappedBy = "categoryUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Category> categories;
    
     
    
}
