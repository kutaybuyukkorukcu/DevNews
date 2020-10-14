package com.scalx.devnews.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment extends BaseEntity {

    @OneToOne(targetEntity = Article.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "article_id", referencedColumnName = "id")
    private Article article;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    private String text;

    // I don't know if i should use Article or String for this one.
    // It just contains parent comments id. Assuming it can't be defined as FK.
    @Column(name = "parent_comment_id")
    private String parentCommentId;
}
