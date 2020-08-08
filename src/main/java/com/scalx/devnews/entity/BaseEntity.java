package com.scalx.devnews.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private boolean isActive;

    private LocalDate CreateDate;

    private LocalDate UpdateDate;

    public boolean isNew() {
        return this.id == null;
    }
}
