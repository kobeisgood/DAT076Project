/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.ejwa.frontend.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    private void prePersist() {
        date = new Date(System.currentTimeMillis());
    }

    @Id
    @GeneratedValue
    private int transactionId;

    @NonNull
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NonNull
    private int amount;

    private String type; // INCOME, SAVINGS, EXPENSE

    @NonNull
    @ManyToOne
    private Category category;

    public int getAmount() {
        if (category.getType().equals("EXPENSE")) {
            return -amount;
        } else if (category.getType().equals("SAVINGS")) {
            return amount;
        } else { // INCOME
            return amount;
        }
    }

}
