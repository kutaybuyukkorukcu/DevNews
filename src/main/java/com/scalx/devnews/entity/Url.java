package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "urls")
public class Url extends BaseEntity<User> {

    @Column(name = "article_link")
    private String articleLink;
}
