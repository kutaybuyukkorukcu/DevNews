package com.scalx.devnews.entity;

import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "likes")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Like extends BaseEntity {

    @Column(name = "title")
    private String title;
    @Column(name = "main_topic")
    private String mainTopic;

}
