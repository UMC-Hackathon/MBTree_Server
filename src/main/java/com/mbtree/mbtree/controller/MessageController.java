package com.mbtree.mbtree.controller;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.dto.Message;
import com.mbtree.mbtree.dto.Messages;
import com.mbtree.mbtree.dto.User;
import com.mbtree.mbtree.repository.MessageRepository;
import com.mbtree.mbtree.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class MessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageRepository messageRepository;


    @Autowired
    private UserRepository userRepository;
/*
    @Autowired
    private PostService postService;
*/
    @PostMapping("/message/send")// 글 작성
    public BaseResponse<Message> postMessage(@RequestBody Messages messages) throws BaseException {
        // url로 userID를 받고, post로 writerID ,content 받아요
        Message message = new Message();
        System.out.println(messages);
        User user = userRepository.findById(Integer.parseInt(messages.getTreeId()));
        User writer = userRepository.findById(Integer.parseInt(messages.getWriterId()));
        message.setTreeId(user);
        message.setWriterId(writer); // 이거 로그인 기능 생기면 구현 예정
        message.setContent(messages.getContent());
        message.setXPos(messages.getXPos());
        message.setYPos((messages.getYPos()));
        messageRepository.save(message);
        return new BaseResponse<>(message);
    }


    @GetMapping("/tree") // 나무 조회
    public BaseResponse<List<Message>> getTree(Model model, @RequestParam(value = "treeId") int treeID){

        try {
            User user = userRepository.findById(treeID);
                if(user ==null ){System.out.println("나무조회 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
            List<Message> messages = messageRepository.findByUserId(treeID);
                 if(messages ==null ){System.out.println("MESSAGES_EMPTY_USER_MESSAGES" ); throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);}
            System.out.println("포스트 리스트 : " + messages); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(messages);
         }
       catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/message") // 메세지 조회
    public BaseResponse<Message> getMessage(Model model, @RequestParam(value = "messageId") int messageId){
        System.out.println("메세지 조회 시작");
        System.out.println("messageId"+messageId);
        try {
            //Message message = postService.messageFindById(messageId);
            Message message = messageRepository.findById(messageId);
            //message 없는 경우
            if(message ==null ){System.out.println("MESSAGES_EMPTY_POST_ID" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
            System.out.println("포스트 Controller message 정보 : " + message);
            return new BaseResponse<>(message);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}