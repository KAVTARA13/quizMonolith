package com.example.monolith.sevice;

import com.example.monolith.model.Question;
import com.example.monolith.model.QuestionWrapper;
import com.example.monolith.model.Quiz;
import com.example.monolith.model.Response;
import com.example.monolith.repository.QuestionRepository;
import com.example.monolith.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        List<Question> questionList = questionRepository.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questionList);
        return new ResponseEntity<>(quizRepository.save(quiz), HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        List<Question> questions = quiz.get().getQuestion();
        List<QuestionWrapper> questionList = new ArrayList<>();

        for (Question question: questions){
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestion_title(question.getQuestion_title());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionList.add(questionWrapper);
        }

        return new ResponseEntity<>(questionList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, Set<Response> response) {
        Quiz quiz = quizRepository.findById(id).get();
        List<Question> questions = quiz.getQuestion();
        int right = 0;
        for (Response res : response){
          for (Question question: questions){
              if (res.getId().equals(question.getId())&res.getResponse().equals(question.getRight_answer())){
                  right++;
              }
          }
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
