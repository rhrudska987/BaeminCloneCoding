package baemin.baeminjpa.controller;

import baemin.baeminjpa.config.BaseException;
import baemin.baeminjpa.config.BaseResponse;
import baemin.baeminjpa.domain.ReceiveStatus;
import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import baemin.baeminjpa.dto.PostUserDto;
import baemin.baeminjpa.service.UserService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입
     * */
    @PostMapping("/sign-up")
    public BaseResponse<PostUserDto> createUser(@Validated @RequestBody PostUserDto postUserReq){
        if(postUserReq.getSMSReceive() == null){
            postUserReq.setSMSReceive(ReceiveStatus.DISAGREE);
        }
        if(postUserReq.getMailReceive() == null){
            postUserReq.setMailReceive(ReceiveStatus.DISAGREE);
        }

        User user = new User(postUserReq.getEmail(), postUserReq.getUserName(), postUserReq.getLoginId(), postUserReq.getPassword(), postUserReq.getMailReceive(), postUserReq.getPhoneNumber(), postUserReq.getSMSReceive());

        userService.createUser(user);
        return new BaseResponse<>(postUserReq);
    }

    /**
     * 특정 회원 조회 API (프로필)
     */
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUserProfile(@PathVariable("userId") int userId){
        GetUserRes getUserRes = userService.findUserInfo(userId);
        return new BaseResponse<>(getUserRes);
    }
}
