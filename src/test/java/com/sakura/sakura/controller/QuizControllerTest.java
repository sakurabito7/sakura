package com.sakura.sakura.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sakura.sakura.entity.Quiz;
import com.sakura.sakura.form.QuizForm;
import com.sakura.sakura.service.QuizService;

public class QuizControllerTest {

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizService quizService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testShowList() {
        QuizForm form = new QuizForm();
        Iterable<Quiz> quizList = Arrays.asList(new Quiz());
        when(quizService.selectAll()).thenReturn(quizList);

        String view = quizController.showList(form, model);

        assertThat(view).isEqualTo("crud");
        verify(model).addAttribute("list", quizList);
        verify(model).addAttribute("title", "登録用フォーム");
    }

    @Test
    public void testInsert_ValidForm() {
        QuizForm form = new QuizForm();
        form.setQuestion("質問？");
        form.setAnswer(true);
        form.setAuthor("テスト");

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = quizController.insert(form, bindingResult, model, redirectAttributes);

        verify(quizService).insertQuiz(any(Quiz.class));
        verify(redirectAttributes).addFlashAttribute(eq("complete"), anyString());
        assertThat(result).isEqualTo("redirect:/quiz");
    }

    @Test
    public void testInsert_InvalidForm() {
        QuizForm form = new QuizForm();
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = quizController.insert(form, bindingResult, model, redirectAttributes);

        assertThat(view).isEqualTo("crud");
    }

    @Test
    public void testShowUpdate() {
        Quiz quiz = new Quiz(1, "Q?", true, "Author");
        when(quizService.selectOneById(1)).thenReturn(Optional.of(quiz));

        QuizForm form = new QuizForm();
        String view = quizController.showUpdate(form, 1, model);

        assertThat(view).isEqualTo("crud");
    }

    @Test
    public void testDelete() {
        String view = quizController.delete("1", model, redirectAttributes);

        verify(quizService).deleteQuizById(1);
        verify(redirectAttributes).addFlashAttribute("delcomplete", "削除が完了しました。");
        assertThat(view).isEqualTo("redirect:/quiz");
    }
}
