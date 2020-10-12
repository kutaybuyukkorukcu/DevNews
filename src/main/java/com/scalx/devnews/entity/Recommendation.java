package com.scalx.devnews.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "recommendations")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Recommendation extends BaseEntity {

    @Column(name = "article_id")
    private int articleId;

    @Column(name = "similarity_score")
    private double similarityScore;
}
