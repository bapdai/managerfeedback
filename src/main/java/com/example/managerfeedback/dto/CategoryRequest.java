package com.example.managerfeedback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    private Date createdAt;
    private String title;
    private String description;
    private String img;
    private String content;
    private int views = 1;
    private Boolean status = true;
    private String author;
    private Set<String> category;
}
