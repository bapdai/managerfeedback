package com.example.managerfeedback.service;

import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAll(){
        return newsRepository.findAll();
    }

    public Optional<News> findById(Integer id){
        return newsRepository.findById(id);
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public void deleteById(Integer id){
        newsRepository.deleteById(id);
    }

    public List<News> getListByStatus(boolean status){
        return newsRepository.findAllByStatus(status);
    }
}
