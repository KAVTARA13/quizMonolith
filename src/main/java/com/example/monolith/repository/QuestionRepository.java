package com.example.monolith.repository;

import com.example.monolith.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q FROM Question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ")
    List<Question> findRandomQuestionsByCategory(String category,int numQ);
}