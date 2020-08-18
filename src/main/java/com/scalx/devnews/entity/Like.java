package com.scalx.devnews.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
public class Like extends AbstractAuditable {

    private String title;
    private String mainTopic;

//    private boolean isNew;
}
