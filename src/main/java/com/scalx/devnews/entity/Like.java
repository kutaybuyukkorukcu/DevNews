package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "likes")
public class Like extends BaseEntity<User> {

    @Column(name = "title")
    private String title;
    @Column(name = "main_topic")
    private String mainTopic;

//    private boolean isNew;
}
