package com.mbtree.mbtree.repository;

import com.mbtree.mbtree.dto.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mbtree.mbtree.config.BaseException;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //@Query(value = "select * from Message where id = ?;" , nativeQuery = true)
    Message findById(int messageId) ;


    @Query(value = "select * from Message where treeId = ?;" , nativeQuery = true)
    List<Message> findByUserId(int treeId) throws BaseException;

}
