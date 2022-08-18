package com.mbtree.mbtree.controller;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.domain.chat.*;
import com.mbtree.mbtree.domain.message.Message;
import com.mbtree.mbtree.domain.message.MessageRepository;
import com.mbtree.mbtree.domain.user.User;
import com.mbtree.mbtree.domain.user.UserRepository;
import com.mbtree.mbtree.dto.chat.ChatMessage;
import com.mbtree.mbtree.dto.chat.ChatRequest;
import com.mbtree.mbtree.dto.chat.ChatResponse;
import com.mbtree.mbtree.dto.chat.MessageType;
import com.mbtree.mbtree.service.chat.ChatService;
import com.mbtree.mbtree.service.chat.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;


@RestController
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatroomRepository chatroomRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatService chatService;

    @Autowired
    private QuizRepository quizRepository;


    // tag :: async
    @GetMapping("chat/join")
    @ResponseBody
    public DeferredResult<ChatResponse> joinRequest(@RequestParam(value = "userId") String userId) {

        logger.info(">> Join request. user id : {}", userId);

        final ChatRequest user = new ChatRequest(userId);
        final DeferredResult<ChatResponse> deferredResult = new DeferredResult<>(null);
        chatService.joinChatRoom(user, deferredResult);

        deferredResult.onCompletion(() -> chatService.cancelChatRoom(user));
        deferredResult.onError((throwable) -> chatService.cancelChatRoom(user));
        deferredResult.onTimeout(() -> chatService.timeout(user));

        return deferredResult;
    }

    @GetMapping("chat/cancel")
    @ResponseBody
    public ResponseEntity<Void> cancelRequest(@RequestParam(value = "userId") String userId) {

        logger.info(">> Cancel request. user id : {}", userId);

        final ChatRequest user = new ChatRequest(userId);
        chatService.cancelChatRoom(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("chat/list")
    public BaseResponse<List<Chatroom>> getChatroom(@RequestParam(value = "userId") String userId) {
        try {
            User user = userRepository.findById(Integer.parseInt(userId));
            if(user ==null ){System.out.println("채팅 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
            List<Chatroom> chatrooms = chatroomRepository.findByOneUser(userId,userId);
            System.out.println("채팅방 리스트 : " + chatrooms); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(chatrooms);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/quit")
    public BaseResponse<Chatroom> getChatroom(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            System.out.println(chatroom);
            if(chatroom == null) {
                System.out.println("CHATROOM_EMPTY");
                throw new BaseException(CHATROOM_EMPTY);
            }
            if(!chatroom.getUser1().equals(userId) && !chatroom.getUser2().equals(userId)){
                System.out.println("CHATROOM_NO_AUTHORITY" );
                throw new BaseException(CHATROOM_NO_AUTHORITY);
            }
            chatroom.setQuit(1);
            chatroomRepository.save(chatroom);
            return new BaseResponse<>(chatroom);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @GetMapping("chat/room")
    public BaseResponse<List<Chat>> quitChatroom(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            System.out.println(chatroom);
            if(chatroom == null) {
                System.out.println("CHATROOM_EMPTY" );
                throw new BaseException(CHATROOM_EMPTY);
            }
            if(!chatroom.getUser1().equals(userId) && !chatroom.getUser2().equals(userId)){
                System.out.println("CHATROOM_NO_AUTHORITY" );
                throw new BaseException(CHATROOM_NO_AUTHORITY);
            }
            List<Chat> chats = chatRepository.findByRoomId(roomId);
            System.out.println("chat 내용 : " + chats);
            return new BaseResponse<>(chats);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/quiz")
    public BaseResponse<String> quizChatroom(@RequestParam(value = "roomId") String roomId) {
        try {
            List<Quiz> quiz = quizRepository.findAll();
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));

            if(chatroom == null) {
                System.out.println("CHATROOM_EMPTY");
                throw new BaseException(CHATROOM_EMPTY);
            }

            String s = chatroom.getQuiz();
            if(s == null) s = "";
            String[] usedQuiz = s.split(" ");
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(usedQuiz));

            System.out.println("quiz 순서 : " + list);

            int size = quiz.size();
            int num;

            if(list.size()==size){
                System.out.println("CHATROOM_NO_QUIZ" );
                throw new BaseException(CHATROOM_NO_QUIZ);
            }

            Random r = new Random();

            do{
                num = r.nextInt(size) + 1;
            }while(list.contains(num));

            String selectQuiz = quizRepository.findById(num).getQuiz();

            String a = s.concat(Integer.toString(num)+" ");
            chatroom.setQuiz(a);
            chatroomRepository.save(chatroom);

            return new BaseResponse<>(selectQuiz);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // tag :: websocket stomp
    @MessageMapping("/chat.message/{chatRoomId}")
    public void sendMessage(@DestinationVariable("chatRoomId") String chatRoomId, @Payload ChatMessage chatMessage) {
        logger.info("Request message. roomd id : {} | chat message : {} | principal : {}", chatRoomId, chatMessage);
        if (!StringUtils.hasText(chatRoomId) || chatMessage == null) {
            return;
        }

        if((chatroomRepository.findById(Integer.parseInt(chatRoomId))).getQuit() == 1) {
            System.out.println("CHATROOM_NO_AUTHORITY" );
            new BaseException(CHATROOM_NO_AUTHORITY);
            return;
        }

        if (chatMessage.getMessageType() == MessageType.CHAT) {
            chatService.sendMessage(chatRoomId, chatMessage);
            Chat chat = new Chat();
            chat.setMessage(chatMessage.getMessage());
            chat.setUserId(chatMessage.getUserId());
            chat.setRoomId(chatRoomId);
            chatRepository.save(chat);
        }
    }
    // -- tag :: websocket stomp
}
