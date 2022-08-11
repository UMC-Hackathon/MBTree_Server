package com.mbtree.mbtree.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbtree.mbtree.config.SecurityProperties;
import com.mbtree.mbtree.dto.KakaoProfile;
import com.mbtree.mbtree.dto.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class LoginController {
    private final ObjectMapper objectMapper;

    @PostMapping("/auth")
    public KakaoProfile KakaoLogin(@RequestParam String code) {

        // 3, 4 : 인증 코드를 받은 후, 위의 파라미터들을 모두 포함해 Access 토큰 요청을 보내고 응답을 받는 코드
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code"); // 고정값
        params.add("client_id", SecurityProperties.client_id);
        params.add("redirect_uri", SecurityProperties.redirect_uri);
        params.add("code", code);

        // HttpHeader 오브젝트 생성
        HttpHeaders headersForAccessToken = new HttpHeaders();
        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headersForAccessToken);
        //POST방식으로 key-value 데이터를 요청(카카오쪽으로)
        RestTemplate rt = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스

        // 실제로 요청하기
        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        OAuthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(accessTokenResponse.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 토큰 전달 받기 완료

        // 5, 6, 7 : 발급받은 Access 토큰으로 API를 호출해서 사용자의 정보를 응답으로 받는 코드
        HttpHeaders headersForRequestProfile = new HttpHeaders();
        headersForRequestProfile.add("Authorization", "Bearer " + Objects.requireNonNull(oauthToken).getAccess_token());
        headersForRequestProfile.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoResourceProfileRequest = new HttpEntity<>(headersForRequestProfile);

        // Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음.
        ResponseEntity<String> resourceProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoResourceProfileRequest,
                String.class
        );

        KakaoProfile profile = null;
        try {
            profile = objectMapper.readValue(resourceProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return profile;
//        User getUser = userService.getUserByEmail(profile.getKakao_account().getEmail());
//        if(getUser==null){
//            UserRequest.join joinUser = new UserRequest.join(profile.getKakao_account().getEmail(), profile.getProperties().getNickname(), "kakao");
//            userService.joinUser(joinUser);
//            return ResponseEntity.ok(new JsonResponse(200,"kakaoLogin",profile));
//        }else{
//            UserRequest.login loginUser = new UserRequest.login(getUser.getEmail(),getUser.getName(),getUser.getLoginId());
//            return ResponseEntity.ok(new JsonResponse(200,"kakaoLogin",loginUser));
//        }
    }
}
