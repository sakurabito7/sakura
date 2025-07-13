package com.sakura.sakura.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** クイズ用テーブル **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("quiz")
public class Quiz {
	
	/** 識別id **/
	@Id
	private Integer id;
	/** クイズ内容 **/
	private String question;
	/** クイズ回答 **/
	private Boolean answer;
	/** 作成者 **/
	private String author;

}
