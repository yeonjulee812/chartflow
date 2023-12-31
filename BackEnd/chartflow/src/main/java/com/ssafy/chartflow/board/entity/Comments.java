package com.ssafy.chartflow.board.entity;

import com.ssafy.chartflow.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content")
    private String content;

    @Column(name = "registertime")
    private LocalDateTime registerTime;

    @OneToMany(mappedBy = "comment")
    private List<ReComments> reComments = new ArrayList<>();
    // getters, setters, etc.

    @Column(name = "cancel")
    private int cancel;

    @Column(name = "modify")
    private int modify;


    public void setArticle(Article article) {
        if (article != null) {
            article.getComments().remove(this);
        }
        this.article = article;
        assert article != null;
        article.getComments().add(this);
    }

    public void setUser(User user) {
        if (user != null) {
            user.getComments().remove(this);
        }
        this.user = user;
        assert user != null;
        user.getComments().add(this);
    }
}