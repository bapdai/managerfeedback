package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByStatus(boolean status);

    Optional<News> findAllByIdAndStatus(Integer id, boolean status);

    List<News> findAllByAuthor(String author);

    List<News> findAllByTitleAndDescription(String title, String description);
}
