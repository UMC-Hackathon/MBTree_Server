package com.mbtree.mbtree.controller;

import static com.mbtree.mbtree.config.BaseResponseStatus.USERS_EMPTY_USER_ID;

import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.config.BaseResponse;
import com.mbtree.mbtree.dto.User;

import com.mbtree.mbtree.dto.Users;
import com.mbtree.mbtree.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users/join") // 회원가입
    public BaseResponse<Users> joinUser(@RequestBody Users user)  {
        //username , userToken , email, character , location post로 받음
      //  try{
            System.out.println("유저" + user);
            User userDTO = new User();
            userDTO.setName(user.getName());
            userDTO.setToken(user.getToken());
            userDTO.setMbti(user.getMbti());
            userDTO.setEmail(user.getEmail());
            userDTO.setUuid(user.getUuid());
            userDTO.setLocation(user.getLocation());
            userDTO.setCreateDate(LocalDateTime.now());
            System.out.println("날짜 안찍힘?" + LocalDateTime.now());
            userRepository.save(userDTO);
            System.out.println("유저" + userDTO);

            return new BaseResponse<>(user);
      //  }
       /* catch (BaseException exception){
            System.out.println("회원가입오류");
            return new BaseResponse<>(exception.getStatus());
        }
    }*/
    }

    @GetMapping("/mypage") //사용자조회 - 마이페이지로 변경
    public BaseResponse<User> readPost(Model model, @RequestParam(value = "userId") int userID){
        System.out.println("userID"+userID);
    try{
        User user = userRepository.findById(userID);
            if(user == null ){System.out.println("마이페이지 USERS_EMPTY_USER_ID" ); throw new BaseException(USERS_EMPTY_USER_ID);}
        System.out.println(user.getName());
        System.out.println("실행잘됨?");
        return new BaseResponse<>(user);
    }
        catch (BaseException exception){
            System.out.println("user오류");
            return new BaseResponse<>(exception.getStatus());
        }
    }

}
