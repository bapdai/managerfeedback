package com.example.managerfeedback.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    private Date createdAt;
    @Column(columnDefinition="text")
    private String title;
    @Column(columnDefinition="text")
    private String description;
    private String img;
    @Column
    private String content;
    private int views = 1;
    private Boolean status = true;
    @Column(columnDefinition="text")
    private String author;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "news_categories", joinColumns = @JoinColumn(name = "news_id")
            , inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public News(Date createdAt, String title, String description, String img, String content, int views, boolean status, String author){
        this.createdAt=createdAt;
        this.title=title;
        this.description=description;
        this.img=img;
        this.content=content;
        this.views=views;
        this.status=status;
        this.author=author;
    }
}