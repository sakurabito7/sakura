package com.sakura.sakura.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.sakura.sakura.entity.Quiz;

public interface QuizRepository extends CrudRepository<Quiz,Integer>{
	
	@Query("SELECT id FROM quiz ORDER BY RAND() LIMIT 1")
	Integer getRandomId();

}
