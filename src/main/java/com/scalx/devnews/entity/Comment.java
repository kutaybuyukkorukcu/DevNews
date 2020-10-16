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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "story_id", referencedColumnName = "id", nullable = false)
    private Story story;

    @Column(name = "author")
    private String author;

    @Column(name = "text")
    private String text;

    @Column(name = "parent_comment_id")
    private int parentCommentId;

    @Column(name = "created")
    private int created;

}
