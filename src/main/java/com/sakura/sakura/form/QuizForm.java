package com.sakura.sakura.form;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizForm {

	// 識別ID
	private Integer id;
	// クイズの内容
	@NotBlank
	private String question;
	// クイズの回答
	private Boolean answer;
	// 作成者
	@NotBlank
	private String author;
	// 登録・変更判定用
	private Boolean newQuiz;
}
