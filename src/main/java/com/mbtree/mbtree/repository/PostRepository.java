package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mbtree.mbtree.config.BaseException;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    //@Query(value = "select * from post where id = ?;" , nativeQuery = true)
    Post findById(int messageId) ;


    @Query(value = "select * from post where user_id = ?;" , nativeQuery = true)
    List<Post> findByUserId(int id) throws BaseException;

}
