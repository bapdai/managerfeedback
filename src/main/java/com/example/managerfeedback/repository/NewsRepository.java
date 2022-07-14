package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByStatus(boolean status);
}
