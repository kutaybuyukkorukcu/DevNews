package com.scalx.devnews.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "recommendations")
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation extends BaseEntity {

    @Column(name = "article_id")
    private int articleId;

    @Column(name = "similarity_score")
    private double similarityScore;
}
