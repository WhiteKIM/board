package com.example.blog.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.blog.Model.KakaoProfile;
import com.example.blog.Model.OAuthToken;
import com.example.blog.Model.User;
import com.example.blog.Service.UserService;
import com.example.blog.config.auth.PrincipalDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URI;
import java.util.UUID;

// 인증이 없는 사용자를 /auth/**

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/auth/joinForm")
    public String joinForm()
    {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm()
    {
        return "user/loginForm";
    }

    @Value("${custom.key}")
    private  String cosKey;

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody ResponseEntity<?> kakaoCallback(String code) throws JsonProcessingException {
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id","a2864b3e87b5436767d580f69ef420ab");
        params.add("redirect_uri", "http://localhost:8090/auth/kakao/callback");
        params.add("code", code);

        //HttpHeader, HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responses = rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        //Gson, Json Simple
        //ObjectMapper 사용
        ObjectMapper obMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = obMapper.readValue(responses.getBody(), OAuthToken.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        System.out.println(oAuthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader, HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        ResponseEntity<String> responses2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        ObjectMapper obMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = obMapper2.readValue(responses2.getBody(), KakaoProfile.class);
        }catch (JsonMappingException e) {
            e.printStackTrace();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        System.out.print("here1");

        //User 오브젝트, username, password, email
        String kakaoUsername = kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId();
        String kakaoUseremail = kakaoProfile.getKakao_account().getEmail();
        String kakaoUserpassword= cosKey;

        User kakaouser= User.builder()
                        .username(kakaoUsername)
                                .password(kakaoUserpassword)
                                        .email(kakaoUseremail)
                .oauth("kakao")
                .build();
        User originuser = userService.findUser(kakaouser.getUsername());
        if(originuser.getUsername() == null)
        {
            userService.register(kakaouser);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.print("here2");

        HttpHeaders redirectHeader = new HttpHeaders();
        redirectHeader.setLocation(URI.create("/"));

        return new ResponseEntity<>(redirectHeader, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal)
    {
        return "user/updateForm";
    }
}
