package com.mbtree.mbtree.post;

import com.mbtree.mbtree.dto.PostDTO;
import com.mbtree.mbtree.dto.UserDTO;
import com.mbtree.mbtree.repository.PostRepository;
import com.mbtree.mbtree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/join") // 회원가입
    public String joinUser(UserDTO user, Model model){
        //username , userToken , email, character , location post로 받음
        userRepository.save(user);
        return "";
    }

    @GetMapping("users")
    public String readPost(Model model, @RequestParam(value = "useridx") int userID){
        UserDTO user = userRepository.findById(userID); //
        System.out.println(user.getUsername()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

}
