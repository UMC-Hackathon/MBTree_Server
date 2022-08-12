package com.mbtree.mbtree.controller.login;

import com.fasterxml.jackson.databind.DatabindContext;
import com.mbtree.mbtree.config.BaseException;
import com.mbtree.mbtree.domain.user.User;
import com.mbtree.mbtree.domain.user.UserRepository;
import com.mbtree.mbtree.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class OAuthController {
    //코드 받아오기
    /**
     * 카카오 callback
     * [GET] /login/kakao/callback
     */

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) throws BaseException {

        User user = new User();

        System.out.println("code : " + code);
        String access_Token = OAuthService.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);

        HashMap<String, Object> userInfo =  OAuthService.getUserInfo(access_Token);
        System.out.println("login Controller : " + userInfo);

        String uuid = (String) userInfo.get("uuid");


        //userRepository에 uuid가 있는지 확인하고, 있으면 넘기고 없으면 저장
        if(userRepository.findByUuid(uuid) == null) {
            user.setUuid((String) userInfo.get("uuid"));
            user.setName((String) userInfo.get("nickname"));
            userRepository.save(user);
        }


        //    클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
//        if (userInfo.get("email") != null) {
//            session.setAttribute("userId", userInfo.get("email"));
//            session.setAttribute("access_Token", access_Token);
//        }
    }
}
