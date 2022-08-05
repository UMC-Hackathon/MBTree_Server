package com.mbtree.mbtree.service;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.config.BaseResponseStatus;
import com.mbtree.mbtree.dto.Post;
import com.mbtree.mbtree.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//아직 사용안함
@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PostRepository postRepository;


    //게시물 가져오기
    public Post messageFindById(int messageId) throws BaseException{
        System.out.println("messageFindById 들어옴 : ");
        Post post = postRepository.findById(messageId);
        if(post ==null ){System.out.println("POSTS_EMPTY_POST" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
        try{

            System.out.println("메세지 정보 : " + post);
            return post;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    };


}
