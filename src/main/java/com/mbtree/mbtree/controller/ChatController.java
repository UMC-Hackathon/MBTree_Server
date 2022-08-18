package com.mbtree.mbtree.controller;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.domain.chat.*;
import com.mbtree.mbtree.dto.chat.ChatroomDto;
import com.mbtree.mbtree.domain.user.User;
import com.mbtree.mbtree.domain.user.UserRepository;
import com.mbtree.mbtree.dto.chat.*;
import com.mbtree.mbtree.service.chat.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
    @GetMapping("chat/join") // 랜덤채팅 매칭
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

    @GetMapping("chat/cancel") // 랜덤채팅 매칭 취소
    @ResponseBody
    public ResponseEntity<Void> cancelRequest(@RequestParam(value = "userId") String userId) {

        logger.info(">> Cancel request. user id : {}", userId);

        final ChatRequest user = new ChatRequest(userId);
        chatService.cancelChatRoom(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("chat/list") // 채팅방 리스트
    public BaseResponse<List<ChatroomDto>> getChatroom(@RequestParam(value = "userId") String userId) {
        try {
            User user = userRepository.findById(Integer.parseInt(userId));
            if(user ==null ){System.out.println("채팅 USERS_EMPTY_USER_ID"); throw new BaseException(USERS_EMPTY_USER_ID);}
            List<Chatroom> chatrooms = chatroomRepository.findByOneUser(userId,userId);
            List<ChatroomDto> chatroomDtoList = new ArrayList<>();
            for(int i=0; i<chatrooms.size(); i++){
                Chatroom c = chatrooms.get(i);
                User user1 = userRepository.findById(Integer.parseInt(c.getUser1()));
                User user2 = userRepository.findById(Integer.parseInt(c.getUser1()));
                ChatroomDto chatroomDto = new ChatroomDto(c.getId(), user1,  user2, c.getQuit());
                chatroomDtoList.add(chatroomDto);
            }
            System.out.println("채팅방 리스트 : " + chatroomDtoList); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(chatroomDtoList);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/list/delete") // 채팅방 리스트 - 채팅방 삭제
    public BaseResponse<List<Chatroom>> deleteChatroom(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId){
        try {
            User user = userRepository.findById(Integer.parseInt(userId));
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));

            if(user ==null ){System.out.println("채팅 USERS_EMPTY_USER_ID"); throw new BaseException(USERS_EMPTY_USER_ID);}
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY"); throw new BaseException(CHATROOM_EMPTY);}

            if(chatroom.getUser1().equals(userId)) {
                chatroom.setView1(1);
            }
            else if(chatroom.getUser2().equals(userId)) {
                chatroom.setView2(1);
            }
            else {System.out.println("CHATROOM_NO_AUTHORITY" ); throw new BaseException(CHATROOM_NO_AUTHORITY);}

            if(chatroom.getView1()==1 && chatroom.getView2()==1){
                chatroom.setQuit(1);
            }
            chatroomRepository.save(chatroom);
            List<Chatroom> chatrooms = chatroomRepository.findByOneUser(userId,userId);
            System.out.println("채팅방 리스트 : " + chatrooms); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(chatrooms);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/quit") // 대화 그만두기
    public BaseResponse<Chatroom> quitChatroom(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            System.out.println(chatroom);
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY");throw new BaseException(CHATROOM_EMPTY);}
            if(!chatroom.getUser1().equals(userId) && !chatroom.getUser2().equals(userId)){
                System.out.println("CHATROOM_NO_AUTHORITY" );throw new BaseException(CHATROOM_NO_AUTHORITY);}
            chatroom.setQuit(1);
            chatroomRepository.save(chatroom);
            return new BaseResponse<>(chatroom);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @GetMapping("chat/room") // 대화방 참여 API -> 이전 대화내용 출력
    public BaseResponse<List<Chat>> getChatroom(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            System.out.println(chatroom);
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY" );throw new BaseException(CHATROOM_EMPTY);}
            if(!chatroom.getUser1().equals(userId) && !chatroom.getUser2().equals(userId)){
                System.out.println("CHATROOM_NO_AUTHORITY" );throw new BaseException(CHATROOM_NO_AUTHORITY);}
            List<Chat> chats = chatRepository.findByRoomId(roomId);
            System.out.println("대화 내용 : " + chats);
            return new BaseResponse<>(chats);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/room/user") // 채팅방 유저정보 출력
    public BaseResponse<List<User>> userChatroom(@RequestParam(value = "roomId") String roomId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY" );throw new BaseException(CHATROOM_EMPTY);}

            List<User> users = new ArrayList<>();
            users.add(userRepository.findById(Integer.parseInt(chatroom.getUser1())));
            users.add(userRepository.findById(Integer.parseInt(chatroom.getUser2())));

            System.out.println("참여중인 유저 : " + users);
            return new BaseResponse<>(users);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/quiz")
    public BaseResponse<Quiz> quizChatroom(@RequestParam(value = "roomId") String roomId) {
        try {
            List<Quiz> quiz = quizRepository.findAll();
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));

            if(chatroom == null) {System.out.println("CHATROOM_EMPTY");throw new BaseException(CHATROOM_EMPTY);}

            String s = chatroom.getQuiz();
            if(s == null) s = "";
            String[] usedQuiz = s.split(" ");
            ArrayList<String> list = new ArrayList<String>(Arrays.asList(usedQuiz));

            int size = quiz.size();
            int num;

            if(list.size()==size){System.out.println("CHATROOM_NO_QUIZ" );throw new BaseException(CHATROOM_NO_QUIZ);}

            Random r = new Random();

            do{
                num = r.nextInt(size) + 1;
            }while(list.contains(num));

            Quiz selectQuiz = quizRepository.findById(num);

            String a = s.concat(Integer.toString(num)+" ");
            chatroom.setAnswer1(0);
            chatroom.setAnswer2(0);
            chatroom.setQuiz(a);
            chatroomRepository.save(chatroom);

            return new BaseResponse<>(selectQuiz);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/answerQuiz") // 사용자 정답 입력
    public BaseResponse<Chatroom> answerQuiz(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId, @RequestParam(value="answer") int answer) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY" );throw new BaseException(CHATROOM_EMPTY);}
            if(!chatroom.getUser1().equals(userId) && !chatroom.getUser2().equals(userId)){
                System.out.println("CHATROOM_NO_AUTHORITY" );throw new BaseException(CHATROOM_NO_AUTHORITY);}

            if(chatroom.getUser1().equals(userId)) {
                chatroom.setAnswer1(answer);
            }
            else if(chatroom.getUser2().equals(userId)) {
                chatroom.setAnswer2(answer);
            }
            else {System.out.println("CHATROOM_NO_AUTHORITY" ); throw new BaseException(CHATROOM_NO_AUTHORITY);}

            chatroomRepository.save(chatroom);

            return new BaseResponse<>(chatroom);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/checkAnswer") // 사용자 정답 비교
    public BaseResponse<Integer> checkAnswer(@RequestParam(value = "roomId") String roomId) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY" );throw new BaseException(CHATROOM_EMPTY);}
            int check;

            if(chatroom.getAnswer1() == 0 || chatroom.getAnswer2() == 0) {
                System.out.println("N0_QUIZ_ANSWER" );throw new BaseException(N0_QUIZ_ANSWER);

            }
            if(chatroom.getAnswer1() == chatroom.getAnswer2()) {
                check = 1;
            }else check = 0;

            return new BaseResponse<>(check);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("chat/checkmbti") // 상대방 MBTI 맞추기
    public BaseResponse<Integer> checkmbti(@RequestParam(value = "roomId") String roomId, @RequestParam(value="userId") String userId, @RequestParam(value="answer") String answer) {
        try {
            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(roomId));
            if(chatroom == null) {System.out.println("CHATROOM_EMPTY" );throw new BaseException(CHATROOM_EMPTY);}
            int check;

            if(chatroom.getUser1().equals(userId)) {
                User user = userRepository.findById(Integer.parseInt(chatroom.getUser2()));
                if(user.getMbti().equals(answer)){
                    check = 1;
                }else check = 0;
            }
            else if(chatroom.getUser2().equals(userId)) {
                User user = userRepository.findById(Integer.parseInt(chatroom.getUser1()));
                if(user.getMbti().equals(answer)){
                    check = 1;
                }else check = 0;
            }
            else {System.out.println("CHATROOM_NO_AUTHORITY" ); throw new BaseException(CHATROOM_NO_AUTHORITY);}
            return new BaseResponse<>(check);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    // tag :: websocket stomp
    @MessageMapping("/chat.message/{chatRoomId}")
    public void sendMessage(@DestinationVariable("chatRoomId") String chatRoomId, @Payload ChatMessage chatMessage) throws BaseException {
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

            Chatroom chatroom = chatroomRepository.findById(Integer.parseInt(chatRoomId));
            chatroom.setView1(0);
            chatroom.setView2(0);
            chatroomRepository.save(chatroom);
            Chat chat = new Chat();
            chat.setMessage(chatMessage.getMessage());
            chat.setUserId(userRepository.findById(Integer.parseInt(chatMessage.getUserId())));
            chat.setRoomId(chatRoomId);
            chatRepository.save(chat);
        }
    }
    // -- tag :: websocket stomp
}
