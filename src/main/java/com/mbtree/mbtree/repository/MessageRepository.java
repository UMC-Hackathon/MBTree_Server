package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mbtree.mbtree.config.BaseException;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //@Query(value = "select * from Message where id = ?;" , nativeQuery = true)
    Message findById(int messageId) ;


   //tree_id에 맞게 나무쪽지리스트뽑아내기
   @Query(value = "select * from message where tree_id = ?;" , nativeQuery = true)
    List<Message> findByUserId(int treeId) throws BaseException;

}
