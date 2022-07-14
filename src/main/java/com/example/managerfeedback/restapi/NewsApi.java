package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
public class NewsApi {
    @Autowired
    NewsService newsService;

    @GetMapping("/views")
    public ResponseEntity<List<News>> getList(){
        return ResponseEntity.ok(newsService.getListByStatus(true));
    }

    @GetMapping("/user/views")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<News>> getListt(){
        return ResponseEntity.ok(newsService.getListByStatus(true));
    }

    @GetMapping("/user/views/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
        Optional<News> optionalNews = newsService.findById(id);
        if (!optionalNews.isPresent()){
            ResponseEntity.badRequest().build();
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }

    @GetMapping("/manager/views")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<News>> getLists(){
        return ResponseEntity.ok(newsService.findAll());
    }

    @GetMapping("/manager/views/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetails(@PathVariable Integer id){
        Optional<News> optionalNews = newsService.findById(id);
        if (!optionalNews.isPresent()){
            ResponseEntity.badRequest().build();
        }
        optionalNews.get().setViews(optionalNews.get().getViews() + 1);
        newsService.save(optionalNews.get());
        return ResponseEntity.ok(optionalNews.get());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<News> create(@RequestBody News news){
        return ResponseEntity.ok(newsService.save(news));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<News> update(@PathVariable Integer id, @RequestBody News news){
        Optional<News> optionalNews = newsService.findById(id);
        if ((!optionalNews.isPresent())){
            ResponseEntity.badRequest().build();
        }
        News existNews = optionalNews.get();

        existNews.setTitle(news.getTitle());
        existNews.setDescription(news.getDescription());
        existNews.setContent(news.getContent());
        existNews.setViews(news.getViews());
        existNews.setStatus(news.getStatus());
        existNews.setAuthor(news.getAuthor());
        return ResponseEntity.ok(newsService.save(existNews));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if ((!newsService.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        newsService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
