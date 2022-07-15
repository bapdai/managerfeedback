package com.example.managerfeedback.repository;

import com.example.managerfeedback.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByStatus(boolean status);

    Optional<Category> findAllByIdAndStatus(Integer id, boolean status);
}
