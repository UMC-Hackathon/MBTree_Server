package com.mbtree.mbtree.post;

import com.mbtree.mbtree.dto.Post;
import com.mbtree.mbtree.dto.Posts;
import com.mbtree.mbtree.dto.User;
import com.mbtree.mbtree.repository.PostRepository;
import com.mbtree.mbtree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/tree/post")// 글 작성 // 잠시보류
    public String postMessage(@RequestBody Posts posts){
        // url로 userID를 받고, post로 writerID ,content 받아요
        Post post = new Post();
        User user = userRepository.findById(Integer.parseInt(posts.getUserId()));
        User writer = userRepository.findById(Integer.parseInt(posts.getWriterId()));
        post.setUserId(user);
        post.setWriterId(writer); // 이거 로그인 기능 생기면 구현 예정
        post.setContent(posts.getContent());
        postRepository.save(post);
        return "index";
    }


    @GetMapping("/tree") // 나무 조회
    public String getTree(Model model, @RequestParam(value = "useridx") int userID){

        List<Post> post = postRepository.findByUserId(userID);

        System.out.println("포스트 갯수 : "+post.size()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

    @GetMapping("/tree/msg")
    public String readPost(Model model , @RequestParam(value = "msgidx") int postID){
        Post post = postRepository.findById(postID); //
        System.out.println("포스트 내용 : "+ post.getContent()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

}
