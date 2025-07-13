package com.sakura.sakura.service;

import java.util.Optional;

import com.sakura.sakura.entity.Quiz;

public interface QuizService {

	// クイズ情報全件取得
	Iterable<Quiz> selectAll();

	// クイズ情報を１件取得
	Optional<Quiz> selectOneById(Integer id);

	// クイズをランダムで１謙取得
	Optional<Quiz> selectOneRandomQuiz();

	// 正解/不正解判定
	Boolean checkQuiz(Integer id, Boolean myAnswer);
	
	// クイズ登録
	void insertQuiz(Quiz quiz);
	
	// クイズ更新
	void updateQuiz(Quiz quiz);
	
	// クイズ削除
	void deleteQuizById(Integer id);

}
