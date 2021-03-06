/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejwa.frontend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "id")
@JsonIdentityReference(alwaysAsId = true)
public class Users implements Serializable {

    @Id @GeneratedValue 
    private int id;
    
    @Column(unique=true)
    @NonNull 
    private String mail;
        
    // @JsonbTransient  ??????? 
    @NonNull
    private String password;

    @JsonbTransient
    @OneToMany(mappedBy = "budgetUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Budget> budgets;
    
    @JsonbTransient
    @OneToMany(mappedBy = "categoryUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Category> categories;
    
     
    
}
