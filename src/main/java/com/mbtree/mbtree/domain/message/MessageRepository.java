package com.mbtree.mbtree.domain.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.mbtree.mbtree.config.BaseException;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    //@Query(value = "select * from Message where id = ?;" , nativeQuery = true)
    Message findById(int messageId) ;


   //tree_id에 맞게 나무쪽지리스트뽑아내기 - 이건 나무에 달린 11개 열매임(안읽음)
   @Query(value = "select * from message where tree_id = ? and is_read = 0 limit 11;" , nativeQuery = true)
    List<Message> findByTreeId(int treeId) throws BaseException;

    //보관함 쪽지 리스트 - 안읽은것 상위 11개 이후
    @Query(value = "SELECT *\n"
        + "  FROM message\n"
        + " WHERE tree_id = ? and is_read = 0\n"
        + "limit 12,  18446744073709551615;" , nativeQuery = true)
    List<Message> findUnReadMessageList(int treeId) throws BaseException;

    //보관함 쪽지 리스트 - 읽은것 전체
    @Query(value = "SELECT *\n"
        + "  FROM message\n"
        + " WHERE tree_id = ? and is_read = 1\n" , nativeQuery = true)
    List<Message> findReadMessageList(int treeId) throws BaseException;

}
