package com.mbtree.mbtree.post;

import com.mbtree.mbtree.dto.User;

import com.mbtree.mbtree.dto.Users;
import com.mbtree.mbtree.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/join") // 회원가입
    public String joinUser(@RequestBody Users user){
        //username , userToken , email, character , location post로 받음
        System.out.println("유저" + user);
        User userDTO = new User();
        userDTO.setName(user.getName());
        userDTO.setUserToken(user.getUserToken());
        userDTO.setMyers(user.getMyers());
        userDTO.setEmail(user.getEmail());
        userDTO.setLocation(user.getLocation());
        userRepository.save(userDTO);
        System.out.println("유저" + userDTO);

        return "index";
    }

    @GetMapping("/users")
    public String readPost(Model model, @RequestParam(value = "useridx") int userID){
        System.out.println("gg");

        User user = userRepository.findById(userID); //
        System.out.println(user.getName()); // 이런식으로 읽을 수 있습니다.
        return "";
    }

}
