package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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

//    @GetMapping("/find/by/views")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<List<News>> getListed(){
//        return ResponseEntity.ok(newsService.getListByViewsDesc(true));
//    }

//    @GetMapping("/find/by/create")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<List<News>> getListes(){
//        return ResponseEntity.ok(newsService.getListByCreatedAtDesc());
//    }



    @GetMapping("/user/views/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
        Optional<News> optionalNews = newsService.getListByIdAndStatus(id,true);
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
        existNews.setImg(news.getImg());
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

    /*
        * Tìm kiếm dữ liệu và phân quyền theo tìm kiếm chức năng
    * */

//    Tìm kiếm theo title
    @GetMapping("/find/user/{keyword}")
    @PreAuthorize("hasRole('USER')")
    public Page<News> getFind(@PathVariable String keyword) {
        return newsService.search(keyword, Pageable.ofSize(10));
    }
    @GetMapping("/find/manager/{keyword}/{abc}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFind(@PathVariable String keyword,@PathVariable boolean abc) {
        return newsService.search(keyword, Pageable.ofSize(10), abc);
    }

//    Tìm kiếm theo từ khóa
    @GetMapping("/find/all/{keyword}")
    @PreAuthorize("hasRole('USER')")
    public Page<News> getFindes(@PathVariable String keyword) {
        return newsService.searches(keyword, Pageable.ofSize(10));
    }

    @GetMapping("/find/all/{keyword}/{abc}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFindes(@PathVariable String keyword,@PathVariable boolean abc) {
        return newsService.searches(keyword, Pageable.ofSize(10), abc);
    }
//    Tin hot
    @GetMapping("/user/find/views/hot")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFindViewUser(){
        Pageable pageable= PageRequest.of(0, 10, Sort.by("views").descending());
        return newsService.getListSortAndTrue(pageable);
    }

    @GetMapping("/manager/find/views/hot")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFindByView(){
        Pageable pageable= PageRequest.of(0, 10, Sort.by("views").descending());
        return newsService.getListSort(pageable);
    }
// Tin mới nhất
    @GetMapping("/user/find/views/new")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFindByCreateAtUser(){
        Pageable pageable= PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return newsService.getListSortAndTrue(pageable);
    }

    @GetMapping("/manager/find/views/new")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Page<News> getFindByCreateAt(){
        Pageable pageable= PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return newsService.getListSort(pageable);
    }
}
