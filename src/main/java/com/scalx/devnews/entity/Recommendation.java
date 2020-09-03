package com.scalx.devnews.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "recommendations")
public class Recommendation extends BaseEntity {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "similarity_score")
    private double similarityScore;

//    private boolean isNew;
}
