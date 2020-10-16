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

    @ManyToOne
    @JoinColumn(name = "story_id", referencedColumnName = "id", nullable = false)
    private Story story;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    private String text;

    // I don't know if i should use Article or String for this one.
    // It just contains parent comments id. Assuming it can't be defined as FK.
    @Column(name = "parent_comment_id")
    private int parentCommentId;

    @Column(name = "created")
    private int created;

}
