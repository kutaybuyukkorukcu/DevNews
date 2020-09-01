package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Like extends BaseEntity<User> {

    private String title;
    private String mainTopic;

//    private boolean isNew;
}
