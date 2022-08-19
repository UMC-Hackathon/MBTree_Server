package com.mbtree.mbtree.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    @Override
    List<Quiz> findAll();

    Quiz findById(int id);
    String findQuizById(int num);
}
