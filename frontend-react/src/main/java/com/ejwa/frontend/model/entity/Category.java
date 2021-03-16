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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 *
 * @author danielkarlkvist
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@IdClass(CategoryPK.class)
public class Category implements Serializable {

    @Id
    @NonNull
    private String categoryName;

    @Id
    @ManyToOne
    @NonNull
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Users categoryUser;

    @NonNull
    private String color;

    @NonNull
    private String type; // INCOME, SAVINGS, EXPENSE

    @JsonbTransient
    @OneToMany(mappedBy = "category", cascade = CascadeType.REFRESH) // ,orphanRemoval = true 
    private List<Transactions> transactions;

}
