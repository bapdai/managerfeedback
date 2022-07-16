package com.example.managerfeedback.repository;


import com.example.managerfeedback.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByStatus(boolean status);

    Optional<News> findAllByIdAndStatus(Integer id, boolean status);

//  Hàm tìm kiếm

//    Optional<News> findAllByAuthor(String author);

//    Optional<News> findAllByAuthorAndStatus(String author, boolean status);

//    Optional<News> findAllByTitleAndDescription(String title, String description);

//    Optional<News> findAllByTitleAndDescriptionAndStatus(String title, String description, boolean status);

//  Tìm kiếm theo cách 2:

//    @Query("SELECT p FROM News p WHERE CONCAT(p.title, p.description, p.status) LIKE %?1%")
//    Optional<News> findAllByTitleAndDescription(String title, String description);

}
