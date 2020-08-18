package com.scalx.devnews.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
public class Recommendation extends AbstractAuditable {

    private Long articleId;
    private double similarityScore;

//    private boolean isNew;
}
