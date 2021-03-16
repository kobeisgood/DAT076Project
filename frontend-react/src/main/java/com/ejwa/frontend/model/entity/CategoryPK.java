package com.ejwa.frontend.model.entity;

import java.io.Serializable;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

// författare marckus akselson

@AllArgsConstructor
public class CategoryPK implements Serializable {

    @Id
    private String categoryName;
    @Id
    private int categoryUser;
    
}
