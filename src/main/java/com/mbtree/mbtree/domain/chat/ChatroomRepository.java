package com.mbtree.mbtree.domain.chat;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.domain.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

    Chatroom findById(int id);

    @Query(value = "select * from chatroom where (user1 = ?1 and user2 = ?2 ) or (user1 = ?2 and user2 = ?1);" , nativeQuery = true)
    Chatroom findByUser(String user1, String user2);

    @Query(value = "select * from chatroom where (user1 = ?1 and view1 != 1) or (user2 = ?2 and view2 != 1);" ,nativeQuery = true)
    List<Chatroom> findByOneUser(String user1, String user2) throws BaseException;

    List<String> findQuizById(int id);

}