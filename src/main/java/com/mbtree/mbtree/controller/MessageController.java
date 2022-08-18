package com.mbtree.mbtree.controller;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.domain.message.Message;
import com.mbtree.mbtree.dto.MessageResponseDto;
import com.mbtree.mbtree.domain.user.User;
import com.mbtree.mbtree.domain.message.MessageRepository;
import com.mbtree.mbtree.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/message/send")// 쪽지전송
    public BaseResponse<Message> postMessage(@RequestBody MessageResponseDto messageResponseDto) {
        // url로 userID를 받고, post로 writerID ,content 받아요
        try {
        Message message = new Message();
        System.out.println(messageResponseDto);
        User tree = userRepository.findById(messageResponseDto.getTreeId());
            if(tree == null ){System.out.println("쪽지 보내기 tree 주인 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
        User writer = userRepository.findById(messageResponseDto.getWriterId());
            if(writer == null ){System.out.println("쪽지 보내기 작성자 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
        message.setTreeId(tree);
        message.setWriterId(writer); // 이거 로그인 기능 생기면 구현 예정 일단은 임시로 값 넘기기
        message.setContent(messageResponseDto.getContent());
        message.setCreateDate(LocalDateTime.now());
        messageRepository.save(message);
        return new BaseResponse<>(message);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


   //나무
   @GetMapping("/tree") // 나무 조회
    public BaseResponse<List<Message>> getTree(Model model, @RequestParam(value = "treeId") int treeID){

        try {
            User user = userRepository.findById(treeID);
                if(user ==null ){System.out.println("나무조회 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
            List<Message> messages = messageRepository.findByTreeId(treeID);
                 if(messages == null ){System.out.println("MESSAGES_EMPTY_USER_MESSAGES" ); throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);}
            System.out.println("포스트 리스트 : " + messages); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(messages);
         }
       catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    //보관함  - 안읽음,읽음 : 이걸 그냥 파라미터로 보내서 함수 내에서 처리하면 코드가 가벼워질텐데 그럼 프론트 단에서 파라미터를 넘겨줘야함
    // isRead 읽음 1 / 안읽음 0
    @GetMapping("/tree/storage") // 보관함 조회
    public BaseResponse<List<Message>> getTreeStorage(Model model, @RequestParam(value = "treeId") int treeID , @RequestParam(value = "isRead") int isRead){

        try {
            User user = userRepository.findById(treeID);
            if(user ==null ){System.out.println("나무 보관함 조회 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}

            //보관함 쪽지 리스트 - 안읽은것 상위 11개 이후
            if(isRead == 0)
            {List<Message> messages = messageRepository.findUnReadMessageList(treeID);
                if(messages == null ){System.out.println("MESSAGES_EMPTY_USER_MESSAGES" ); throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);}
                System.out.println("보관함 쪽지 리스트 - 안읽은것 상위 11개 이후 : " + messages); // 이런식으로 읽을 수 있습니다.
                return new BaseResponse<>(messages);}
            //보관함 쪽지 리스트 - 읽은것 전체
            else if (isRead == 1) {List<Message> messages = messageRepository.findReadMessageList(treeID);
                if(messages == null ){System.out.println("MESSAGES_EMPTY_USER_MESSAGES" ); throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);}
                System.out.println("보관함 쪽지 리스트 - 읽은것 전체 : " + messages); // 이런식으로 읽을 수 있습니다.
                return new BaseResponse<>(messages);
            }
            else throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);



        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    @GetMapping("/message") // 메세지 조회
    public BaseResponse<Message> getMessage(@RequestParam(value = "messageId") int messageId){
        System.out.println("메세지 조회 시작");
        System.out.println("messageId"+messageId);
        try {
            //Message message = postService.messageFindById(messageId);
            Message message = messageRepository.findById(messageId);
            //message 없는 경우
            if(message ==null ){System.out.println("MESSAGES_EMPTY_POST_ID" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
            System.out.println("포스트 Controller message 정보 : " + message);
            if(message.getIsRead()==0){System.out.println("메세지 아직 안읽음");message.setIsRead(1);}
            messageRepository.save(message);
            return new BaseResponse<>(message);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
    
    @DeleteMapping("/message/delete")
    public BaseResponse<Message> delete (@RequestParam(value = "messageId") int messageId){
        
        try {
            Message message = messageRepository.findById(messageId);
            if(message ==null ){System.out.println("삭제중 쪽제 아이디 오류" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
            messageRepository.delete(message);
            return new BaseResponse<>(message);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
