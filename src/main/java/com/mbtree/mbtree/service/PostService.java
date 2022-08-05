package com.mbtree.mbtree.service;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.dto.Message;
import com.mbtree.mbtree.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//아직 사용안함
@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageRepository messageRepository;


    //게시물 가져오기
    public Message messageFindById(int messageId) throws BaseException{
        System.out.println("messageFindById 들어옴 : ");
        Message message = messageRepository.findById(messageId);
        if(message ==null ){System.out.println("POSTS_EMPTY_POST" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
        try{

            System.out.println("메세지 정보 : " + message);
            return message;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    };


}
