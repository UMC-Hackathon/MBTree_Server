package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findById(int id);
    // select * frow post where id = ?

    @Query(value = "select * from post where user_id = ?;" , nativeQuery = true)
    List<Post> findByUserId(int id);
}
