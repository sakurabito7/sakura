package com.sakura.sakura.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sakura.sakura.entity.Quiz;
import com.sakura.sakura.repository.QuizRepository;

public class QuizServiceImplTest {

    @InjectMocks
    private QuizServiceImpl quizService;

    @Mock
    private QuizRepository quizRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSelectAll() {
        List<Quiz> quizList = Arrays.asList(new Quiz(1, "Q1", true, "Author"));
        when(quizRepository.findAll()).thenReturn(quizList);

        Iterable<Quiz> result = quizService.selectAll();
        assertThat(result).containsExactlyElementsOf(quizList);
    }

    @Test
    public void testSelectOneById() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Optional<Quiz> result = quizService.selectOneById(1);
        assertThat(result).isPresent().contains(quiz);
    }

    @Test
    public void testSelectOneRandomQuiz_found() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        when(quizRepository.getRandomId()).thenReturn(1);
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Optional<Quiz> result = quizService.selectOneRandomQuiz();
        assertThat(result).isPresent().contains(quiz);
    }

    @Test
    public void testSelectOneRandomQuiz_notFound() {
        when(quizRepository.getRandomId()).thenReturn(null);
        Optional<Quiz> result = quizService.selectOneRandomQuiz();
        assertThat(result).isEmpty();
    }

    @Test
    public void testCheckQuiz_correct() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Boolean result = quizService.checkQuiz(1, true);
        assertThat(result).isTrue();
    }

    @Test
    public void testCheckQuiz_incorrect() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        when(quizRepository.findById(1)).thenReturn(Optional.of(quiz));

        Boolean result = quizService.checkQuiz(1, false);
        assertThat(result).isFalse();
    }

    @Test
    public void testInsertQuiz() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        quizService.insertQuiz(quiz);
        verify(quizRepository).save(quiz);
    }

    @Test
    public void testUpdateQuiz() {
        Quiz quiz = new Quiz(1, "Q1", true, "Author");
        quizService.updateQuiz(quiz);
        verify(quizRepository).save(quiz);
    }

    @Test
    public void testDeleteQuizById() {
        quizService.deleteQuizById(1);
        verify(quizRepository).deleteById(1);
    }
}
