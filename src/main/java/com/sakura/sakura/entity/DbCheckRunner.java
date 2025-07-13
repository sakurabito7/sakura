package com.sakura.sakura.entity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sakura.sakura.repository.QuizRepository;

@Component
public class DbCheckRunner implements CommandLineRunner {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	QuizRepository repository;

	public DbCheckRunner(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void run(String... args) {
		try {
			List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT * FROM quiz");

			if (results.isEmpty()) {
				System.out.println("⚠️ クイズテーブルにデータがありません。");
			} else {
				System.out.println("DB接続成功！");
			}
			
//			getAll();
//			
//			update();
//			
//			getbyId();

		} catch (Exception e) {
			System.err.println("❌ DBエラー: " + e.getMessage());
		}
	}

	// 全件取得
	private void getAll() {
		System.out.println("--- 全件取得開始");
		Iterable<Quiz> quizzes = repository.findAll();
		for (Quiz quiz : quizzes) {
			System.out.println(quiz);
		}
		System.out.println("--- 全件取得終了");
	}

	// １件取得
	private void getbyId() {
		System.out.println("--- １件取得開始");
		Optional<Quiz> quiz = repository.findById(1);
		if (quiz.isPresent()) System.out.println(quiz);
		System.out.println("--- １件取得終了");
	}

	// 更新
	private void update() {
		System.out.println("--- 更新開始");
		Quiz quiz = new Quiz(1,"ここは日本ですか？",true,"櫻井だ！");
		quiz = repository.save(quiz);
		System.out.println("更新したデータは" + quiz + "です。");
		System.out.println("--- 更新終了");
	}
	
	
	
}