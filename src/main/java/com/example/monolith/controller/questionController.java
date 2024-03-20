package com.example.monolith.controller;

import com.example.monolith.model.Question;
import com.example.monolith.sevice.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class questionController {

    private final QuestionService questionService;

    public questionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }
}
