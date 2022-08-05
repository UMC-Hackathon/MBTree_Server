package com.mbtree.mbtree.post;

import static com.mbtree.mbtree.config.BaseResponseStatus.*;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.config.BaseResponseStatus;
import com.mbtree.mbtree.dto.Post;
import com.mbtree.mbtree.dto.Posts;
import com.mbtree.mbtree.dto.User;
import com.mbtree.mbtree.service.PostService;
import com.mbtree.mbtree.repository.PostRepository;
import com.mbtree.mbtree.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PostRepository postRepository;


    @Autowired
    private UserRepository userRepository;
/*
    @Autowired
    private PostService postService;
*/
    @PostMapping("/message/send")// 글 작성
    public BaseResponse<Post> postMessage(@RequestBody Posts posts) throws BaseException {
        // url로 userID를 받고, post로 writerID ,content 받아요
        Post post = new Post();
        System.out.println(posts);
        User user = userRepository.findById(Integer.parseInt(posts.getUserId()));
        User writer = userRepository.findById(Integer.parseInt(posts.getWriterId()));
        post.setUserId(user);
        post.setWriterId(writer); // 이거 로그인 기능 생기면 구현 예정
        post.setContent(posts.getContent());
        post.setXPos(Float.parseFloat(posts.getXPos()));
        post.setYPos(Float.parseFloat(posts.getYPos()));
        postRepository.save(post);
        return new BaseResponse<>(post);
    }


    @GetMapping("/tree") // 나무 조회
    public BaseResponse<List<Post>> getTree(Model model, @RequestParam(value = "userId") int userID){

        try {
            User user = userRepository.findById(userID);
                if(user ==null ){System.out.println("나무조회 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
            List<Post> posts = postRepository.findByUserId(userID);
                 if(posts ==null ){System.out.println("MESSAGES_EMPTY_USER_MESSAGES" ); throw new BaseException(MESSAGES_EMPTY_USER_MESSAGES);}
            System.out.println("포스트 리스트 : " + posts); // 이런식으로 읽을 수 있습니다.
            return new BaseResponse<>(posts);
         }
       catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/message") // 메세지 조회
    public BaseResponse<Post> getMessage(Model model, @RequestParam(value = "messageId") int messageId){
        System.out.println("메세지 조회 시작");

        try {
            //Post post = postService.messageFindById(messageId);
            Post post = postRepository.findById(messageId);
            //message 없는 경우
            if(post ==null ){System.out.println("MESSAGES_EMPTY_POST_ID" ); throw new BaseException(MESSAGES_EMPTY_POST_ID);}
            System.out.println("포스트 Controller message 정보 : " + post);
            return new BaseResponse<>(post);
        }
        catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
