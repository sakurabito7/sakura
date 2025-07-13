package com.sakura.sakura.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sakura.sakura.entity.Quiz;
import com.sakura.sakura.repository.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

	// DI
	@Autowired
	QuizRepository rp;

	@Override
	public Iterable<Quiz> selectAll() {
		return rp.findAll();
	}

	@Override
	public Optional<Quiz> selectOneById(Integer id) {
		return rp.findById(id);
	}

	@Override
	public Optional<Quiz> selectOneRandomQuiz() {
		// ランダムIDの値を取得する
		Integer randId = rp.getRandomId();
		if(randId == null) {
			return Optional.empty();
		}
		return rp.findById(randId);
	}

	@Override
	public Boolean checkQuiz(Integer id, Boolean myAnswer) {
		// 対象のクイズを取得
		Optional<Quiz> optQuiz = rp.findById(id);
		// 値存在チェック
		if (optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();
			if (quiz.getAnswer().equals(myAnswer)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void insertQuiz(Quiz quiz) {
		rp.save(quiz);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		rp.save(quiz);
	}

	@Override
	public void deleteQuizById(Integer id) {
		rp.deleteById(id);
	}

}
