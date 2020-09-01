package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Url extends BaseEntity<User> {

    private String articleLink;

    private boolean isNew;

}
