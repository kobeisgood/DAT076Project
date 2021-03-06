/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ejwa.frontend.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
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
@IdClass(BudgetPK.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
      property = "compoundKey")
@JsonIdentityReference(alwaysAsId = true)
public class Budget implements Serializable {

    @Id @NonNull
    private String budgetName;
    
    @Id @ManyToOne @NonNull 
    private Users budgetUser;
    
    @JsonbTransient
    @OneToMany(mappedBy = "budget", cascade = CascadeType.REFRESH)
    private List<Transactions> transactions;

    public BudgetPK getCompoundKey(){
        return new BudgetPK(budgetName, budgetUser.getId());
    }
    
    
}
