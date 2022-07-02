package com.mbtree.mbtree.post;

import com.mbtree.mbtree.dto.Post;
import com.mbtree.mbtree.dto.PostDTO;
import com.mbtree.mbtree.dto.TreeDTO;
import com.mbtree.mbtree.dto.UserDTO;
import com.mbtree.mbtree.repository.PostRepository;
import com.mbtree.mbtree.repository.TreeRepository;
import com.mbtree.mbtree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/tree/post") // 글 작성 // 잠시보류
    public String postMessage(Model model, PostDTO post, @RequestParam(value = "useridx") int userID){
        // url로 userID를 받고, post로 writerID ,content 받아요!
        TreeDTO tree = treeRepository.findByUserId(userID);
        post.setTreeID(tree);
        //post.setWriterID(userRepository.findById(userID)); // 이거 로그인 기능 생기면 구현 예정
        post.setContent(post.getContent());
        postRepository.save(post);
        return "";
    }

    @GetMapping("/tree") // 나무 조회
    public String getTree(Model model, @RequestParam(value = "useridx") int userID){

        TreeDTO tree = treeRepository.findByUserId(userID);
        List<PostDTO> post = postRepository.findByTreeID(tree.getId());

        System.out.println("포스트 갯수 : "+post.size()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

    @GetMapping("/tree/msg")
    public String readPost(Model model , @RequestParam(value = "msgidx") int postID){
        PostDTO post = postRepository.findById(postID); //
        System.out.println("포스트 내용 : "+post.getContent()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

}
