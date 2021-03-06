package com.example.managerfeedback.service;

import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Integer id){
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Integer id){
        categoryRepository.deleteById(id);
    }

//    public List<Category> findAllByStatus(Boolean status){
//        return categoryRepository.findAllByStatus(status);
//    }

//    public Optional<Category> getListByIdAndStatus(Integer id, boolean status){
//        return categoryRepository.findAllByIdAndStatus(id, status);
//    }
}
