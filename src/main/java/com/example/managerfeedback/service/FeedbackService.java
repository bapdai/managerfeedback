package com.example.managerfeedback.service;

import com.example.managerfeedback.entity.Feedbacks;
import com.example.managerfeedback.repository.FeedbacksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    private FeedbacksRepository feedbacksRepository;

    public List<Feedbacks> findAll(){
        return feedbacksRepository.findAll();
    }

    public Optional<Feedbacks> findById(Integer id){
        return feedbacksRepository.findById(id);
    }

    public Feedbacks save(Feedbacks feedbacks) {
        return feedbacksRepository.save(feedbacks);
    }

    public void deleteById(Integer id){
        feedbacksRepository.deleteById(id);
    }

    public List<Feedbacks> getListByStatus(boolean status){
        return feedbacksRepository.findAllByStatus(status);
    }

    public Optional<Feedbacks> getListByIdAndStatus(Integer id, boolean status){
        return feedbacksRepository.findAllByIdAndStatus(id, status);
    }
}
