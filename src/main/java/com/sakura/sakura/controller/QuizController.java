package com.sakura.sakura.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sakura.sakura.entity.Quiz;
import com.sakura.sakura.form.QuizForm;
import com.sakura.sakura.service.QuizService;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	// DI
	@Autowired
	QuizService service;

	// backing beenの初期化
	@ModelAttribute
	public QuizForm setUpForm() {
		QuizForm form = new QuizForm();
		// ラジオボタンのデフォルト値設定
		form.setAnswer(true);
		return form;
	}

	// quiz一覧
	@GetMapping
	public String showList(QuizForm quizForm, Model model) {
		// 新規登録
		quizForm.setNewQuiz(true);
		// 掲示板の一覧取得
		Iterable<Quiz> list = service.selectAll();
		model.addAttribute("quizlist", list);
		model.addAttribute("title", "登録用フォーム");
		return "crud";
	}

	// 1件登録
	@PostMapping("/insert")
	public String insert(@Validated QuizForm quizForm, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		// FormからDataへの詰め替え
		Quiz quiz = new Quiz();
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());

//		ModelMapper mapper = new ModelMapper();
//		Quiz quiz = mapper.map(quizForm, Quiz.class);
		// 入力チェック
		if (!bindingResult.hasErrors()) {
			service.insertQuiz(quiz);
			redirectAttributes.addFlashAttribute("resultMessage", "登録が完了しました。");
			return "redirect:/quiz";
		} else {
			return showList(quizForm, model);
		}
	}

	// 1件表示
	@GetMapping("/{id}")
	public String showUpdate(QuizForm quizForm, @PathVariable Integer id, Model model) {
		// Quizを取得
		Optional<Quiz> quizOpt = service.selectOneById(id);
		Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
		if (quizFormOpt.isPresent()) {
			quizForm = quizFormOpt.get();
		}
		// 更新用Model作成
		makeUpdateModel(quizForm, model);
		return "crud";
	}

	// 更新用Model作成
	private void makeUpdateModel(QuizForm quizForm, Model model) {
		model.addAttribute("id", quizForm.getId());
		quizForm.setNewQuiz(false);
		model.addAttribute("quizForm", quizForm);
		model.addAttribute("title", "更新用フォーム");
	}

	// 更新
	@PostMapping("/update")
	public String update(@Validated QuizForm quizForm, BindingResult result, Model model,
			RedirectAttributes redirectAttributes) {
		Quiz quiz = makeQuiz(quizForm);
		if (!result.hasErrors()) {
			service.updateQuiz(quiz);
			redirectAttributes.addFlashAttribute("resultMessage", "更新が終了しました。");
			return "redirect:/quiz/" + quiz.getId();
		} else {
			makeUpdateModel(quizForm, model);
			return "crud";
		}
	}

	// クイズで遊ぶ
	@GetMapping("/play")
	public String showQuiz(QuizForm quizForm, Model model) {
		Optional<Quiz> quizOpt = service.selectOneRandomQuiz();
		if (quizOpt.isPresent()) {
			Optional<QuizForm> quizFormOpt = quizOpt.map(t -> makeQuizForm(t));
			quizForm = quizFormOpt.get();

		} else {
			model.addAttribute("msg", "問題がありません。。");
			return "play";
		}
		model.addAttribute("quizForm", quizForm);
		return "play";
	}

	// クイズの判定
	@PostMapping("/check")
	public String checkQuiz(QuizForm quizForm, @RequestParam Boolean answer, Model model) {
		if (service.checkQuiz(quizForm.getId(), answer)) {
			model.addAttribute("msg", "正解です！");
		} else {
			model.addAttribute("msg", "残念、不正解です。");
		}
		return "answer";
	}

	// 削除
	@PostMapping("/delete")
	public String delete(@RequestParam("id") String id, Model model, RedirectAttributes redirectAttributes) {
		service.deleteQuizById(Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("delresultMessage", "削除が完了しました。");
		return "redirect:/quiz";
	}

	private Quiz makeQuiz(QuizForm quizForm) {
		Quiz quiz = new Quiz();
		quiz.setId(quizForm.getId());
		quiz.setQuestion(quizForm.getQuestion());
		quiz.setAnswer(quizForm.getAnswer());
		quiz.setAuthor(quizForm.getAuthor());
		
		// FormからDataへの詰め替え
//		ModelMapper mapper = new ModelMapper();
//		Quiz quiz = mapper.map(quizForm, Quiz.class);
		
		// 
		return quiz;
	}

	private QuizForm makeQuizForm(Quiz quiz) {
  		QuizForm form = new QuizForm();
		form.setId(quiz.getId());
		form.setQuestion(quiz.getQuestion());
		form.setAnswer(quiz.getAnswer());
		form.setAuthor(quiz.getAuthor());
		form.setNewQuiz(false);
		
		// DataからFormへの詰め替え
		//ModelMapper mapper = new ModelMapper();
		//QuizForm form = mapper.map(quiz, QuizForm.class);
		form.setNewQuiz(false);
		return form;
	}

}
