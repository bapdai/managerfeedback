package com.example.managerfeedback.restapi;

import com.example.managerfeedback.entity.Category;
import com.example.managerfeedback.entity.News;
import com.example.managerfeedback.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryApi {

    @Autowired
    CategoryService categoryService;

//    @GetMapping("/all")
//    public ResponseEntity<List<Category>> getList(){
//        return ResponseEntity.ok(categoryService.findAllByStatus(true));
//    }

    @GetMapping("/user/views")
    public ResponseEntity<List<Category>> getListt(){
//        return ResponseEntity.ok(categoryService.findAllByStatus(true));
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/user/views/{id}")
    public ResponseEntity<?> getDetail(@PathVariable Integer id){
//        Optional<Category> optionalCategory = categoryService.getListByIdAndStatus(id, true);
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (!optionalCategory.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    @GetMapping("/manager/views")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Category>> getLists(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/manager/views/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getDetails(@PathVariable Integer id){
        Optional<Category> optionalCategory = categoryService.findById(id);
        if (!optionalCategory.isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalCategory.get());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Category> create(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.save(category));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category){
        Optional<Category> optionalCategory = categoryService.findById(id);
        if ((!optionalCategory.isPresent())){
            ResponseEntity.badRequest().build();
        }
        Category existCategory = optionalCategory.get();

        existCategory.setName(category.getName());
//        existCategory.setStatus(category.getStatus());
        return ResponseEntity.ok(categoryService.save(existCategory));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if ((!categoryService.findById(id).isPresent())){
            ResponseEntity.badRequest().build();
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
