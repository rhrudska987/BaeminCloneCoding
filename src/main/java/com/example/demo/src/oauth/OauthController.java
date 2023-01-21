package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.oauth.model.PostKakaoCreateUserReq;
import com.example.demo.src.oauth.model.PostKakaoUserRes;
import com.example.demo.src.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("oauth")
public class OauthController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OauthService oauthService;
    @Autowired
    private final UserService userService;

    public OauthController(OauthService oauthService, UserService userService){
        this.oauthService = oauthService;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/kakao")
    public BaseResponse<PostKakaoUserRes> kakaoCallback(@RequestParam("code") String code, HttpSession httpSession) {
        System.out.println("code = " + code);
        String access_Token = oauthService.getKakaoAccessToken(code);
        PostKakaoUserRes userInfo = oauthService.GetUserInfo(access_Token);
        return new BaseResponse<>(userInfo);
    }

    @ResponseBody
    @PostMapping("/kakao/sign-up")
    public BaseResponse<String> createUser(@RequestHeader int userId, @RequestBody PostKakaoCreateUserReq postKakaoCreateUserReq) throws BaseException {
        if(postKakaoCreateUserReq.getPhoneNumber() == null){
            return new BaseResponse<>(POST_STORE_EMPTY_TELEPHONENUMBER);
        }
        if(postKakaoCreateUserReq.getContract1().equals("N")){
            return new BaseResponse<>(NOT_ACCEPT_CONTRACT1);
        }
        if(postKakaoCreateUserReq.getContract2().equals("N")){
            return new BaseResponse<>(NOT_ACCEPT_CONTRACT2);
        }
        if(postKakaoCreateUserReq.getContract3().equals("N")){
            return new BaseResponse<>(NOT_ACCEPT_CONTRACT3);
        }
        oauthService.createUser(postKakaoCreateUserReq, userId);
        return new BaseResponse<>();
    }

}
