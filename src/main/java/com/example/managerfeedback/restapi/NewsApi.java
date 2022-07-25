package com.example.managerfeedback.restapi;

import com.example.managerfeedback.dto.CategoryRequest;
import com.example.managerfeedback.dto.MessageResponse;
import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.entity.Role;
import com.example.managerfeedback.repository.CategoryRepository;
import com.example.managerfeedback.service.NewsService;
import com.example.managerfeedback.util.ECategory;
import com.example.managerfeedback.util.ERole;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
public class NewsApi {
    @Autowired
    NewsService newsService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/views")
    public ResponseEntity<List<News>> getList(){
        return ResponseEntity.ok(newsService.getListByStatus(true));
    }

    @GetMapping("/user/views")
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
    public ResponseEntity<?> create( @RequestBody CategoryRequest categoryRequest){
        News news = new News(categoryRequest.getCreatedAt(),
                categoryRequest.getTitle(),
                categoryRequest.getDescription(),
                categoryRequest.getImg(),
                categoryRequest.getContent(),
                categoryRequest.getViews(),
                categoryRequest.getStatus(),
                categoryRequest.getAuthor());
        Set<String> strCategory = categoryRequest.getCategory();
        Set<Category> category = new HashSet<>();
        if (strCategory == null){
            Category newsCategory = categoryRepository.findByName(ECategory.HOMEPAGE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            category.add(newsCategory);
        }else {
            strCategory.forEach(cate -> {
                switch (cate){
                    case "homepage":
                        Category homepageCategory = categoryRepository.findByName(ECategory.HOMEPAGE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(homepageCategory);
                        break;
                    case "political":
                        Category politicalCategory = categoryRepository.findByName(ECategory.POLITICAL)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(politicalCategory);
                        break;
                    case "social":
                        Category socialCategory = categoryRepository.findByName(ECategory.SOCIAL)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(socialCategory);
                        break;
                    case "economy":
                        Category economyCategory = categoryRepository.findByName(ECategory.ECONOMY)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(economyCategory);
                        break;
                    case "health":
                        Category healthCategory = categoryRepository.findByName(ECategory.HEALTH)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(healthCategory);
                        break;
                    case "education":
                        Category educationCategory = categoryRepository.findByName(ECategory.EDUCATION)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(educationCategory);
                        break;
                    case "law":
                        Category lawCategory = categoryRepository.findByName(ECategory.LAW)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(lawCategory);
                        break;
                    case "sport":
                        Category sportCategory = categoryRepository.findByName(ECategory.SPORT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(sportCategory);
                        break;
                    default:
                        Category worldCategory = categoryRepository.findByName(ECategory.WORLD)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        category.add(worldCategory);
                }
            });
        }
        news.setCategories(category);
        newsService.save(news);
        return ResponseEntity.ok(new MessageResponse("News has been added successfully!"));
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
        existNews.setCategories(news.getCategories());
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
