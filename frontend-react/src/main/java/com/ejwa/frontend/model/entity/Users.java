/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejwa.frontend.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
    private String firstName;
    
    @NonNull 
    private String lastName;
        
    // @JsonbTransient  ??????? 
    @NonNull
    private String password;
    
    @JsonbTransient
    @OneToMany(mappedBy = "categoryUser", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Category> categories;
    
     
    
}
