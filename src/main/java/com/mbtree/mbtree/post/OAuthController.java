package com.mbtree.mbtree.post;

import com.mbtree.mbtree.service.OAuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/login")
public class OAuthController {
    //코드 받아오기
    /**
     * 카카오 callback
     * [GET] /login/kakao/callback
     */
    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        System.out.println("code : " + code);
        String access_Token = OAuthService.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
    }
}
