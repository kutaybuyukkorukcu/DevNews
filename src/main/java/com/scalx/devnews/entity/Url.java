package com.scalx.devnews.entity;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "urls")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Url extends BaseEntity {

    @Column(name = "article_link")
    private String articleLink;
}
