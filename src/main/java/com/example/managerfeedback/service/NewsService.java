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

    public Optional<News> getListByIdAndStatus(Integer id, boolean status){
        return newsRepository.findAllByIdAndStatus(id, status);
    }

//    public List<News> getListByViewsDesc(boolean status){
//        return newsRepository.findAllByViewsDescAndStatus(status);
//    }


//    List<News> getListed(){
//        return newsRepository.();
//    }

//    public Optional<News> getListByTitleAndDescription(String title, String description){
//        return newsRepository.findAllByTitleAndDescription(title, description);
//    }

//    public Optional<News> getListByTitleAndDescriptionAndStatus(String title, String description, boolean status){
//        return newsRepository.findAllByTitleAndDescriptionAndStatus(title, description, status);
//    }
//    public Optional<News> getListByAuthor(String author){
//        return newsRepository.findAllByAuthor(author);
//    }

//    public Optional<News> getListByAuthorAndStatus(String author, boolean status){
//        return newsRepository.findAllByAuthorAndStatus(author, status);
//    }

//    tìm kiếm theo views(tin hot)
//        List<News> list = newsRepository.findAll();
//        Collections.sort(list, (s1, s2) -> s1.getViews().compareTo(s2.getViews()) > 1 ? 1 : s1.getViews().compareTo(s2.getViews()) < 1 ? -1 : 0);
//        return list;

//
//    public Optional<News> listAll(String keyword1) {
//        String keyword = "iPhone";
//        return newsRepository.findAllByTitleAndDescription(keyword, keyword);
//    }
}
