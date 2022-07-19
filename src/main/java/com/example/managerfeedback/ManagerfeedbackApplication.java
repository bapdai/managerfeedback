package com.example.managerfeedback;

import com.example.managerfeedback.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Pageable;

@SpringBootApplication
public class ManagerfeedbackApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerfeedbackApplication.class, args);
    }

}
