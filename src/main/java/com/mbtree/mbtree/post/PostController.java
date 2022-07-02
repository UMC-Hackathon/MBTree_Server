package com.mbtree.mbtree.post;

import com.mbtree.mbtree.dto.PostDTO;
import com.mbtree.mbtree.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/read/post")
    public String readPost(Model model, @RequestParam(value = "id") int postID){
        PostDTO post = postRepository.findById(postID); //
        System.out.println(post.getContent()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

}
