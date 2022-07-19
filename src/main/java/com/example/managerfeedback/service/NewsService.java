package com.example.managerfeedback.service;

import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public Optional<News> findById(Integer id) {
        return newsRepository.findById(id);
    }

    public News save(News news) {
        return newsRepository.save(news);
    }

    public void deleteById(Integer id) {
        newsRepository.deleteById(id);
    }

    public List<News> getListByStatus(boolean status) {
        return newsRepository.findAllByStatus(status);
    }

    public Optional<News> getListByIdAndStatus(Integer id, boolean status) {
        return newsRepository.findAllByIdAndStatus(id, status);
    }

//    tìm kiếm theo views(tin hot)
//        List<News> list = newsRepository.findAll();
//        Collections.sort(list, (s1, s2) -> s1.getViews().compareTo(s2.getViews()) > 1 ? 1 : s1.getViews().compareTo(s2.getViews()) < 1 ? -1 : 0);
//        return list;

    //    Tìm kiếm theo title
    public Page<News> search(String title, Pageable pageable, boolean abc) {
        return newsRepository.filter(title, pageable, abc);
    }

    public Page<News> search(String title, Pageable pageable) {
        return newsRepository.filter(title, pageable);
    }
//    Tìm kiếm theo từ khóa

    public Page<News> searches(String title, Pageable pageable, boolean abc) {
        return newsRepository.filters(title, pageable, abc);
    }

    public Page<News> searches(String title, Pageable pageable) {
        return newsRepository.filters(title, pageable);
    }

    public List<News> getFindByView(int views, Sort sort){
        return newsRepository.findAllByView(views, sort);
    }

    public List<News> getFindByCreateAt(){
        return newsRepository.findAllByCreateAtDesc();
    }
}