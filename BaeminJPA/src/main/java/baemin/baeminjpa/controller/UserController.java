package baemin.baeminjpa.controller;

import baemin.baeminjpa.repository.UserRepository;
import baemin.baeminjpa.config.BaseResponse;
import baemin.baeminjpa.domain.MemberReview;
import baemin.baeminjpa.domain.ReceiveStatus;
import baemin.baeminjpa.domain.User;
import baemin.baeminjpa.dto.GetUserRes;
import baemin.baeminjpa.dto.PostUserDto;
import baemin.baeminjpa.provider.UserProvider;
import baemin.baeminjpa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "Member api")
@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;

    /**
     * 회원가입
     * */
    @Operation(summary = "일반 회원가입", description = "회원 가입 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
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
    @Operation(summary = "회원 프로필 조회", description = "회원 프로필 조회 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping("/{userId}")
    public BaseResponse<GetUserRes> getUserProfile(@PathVariable("userId") Long userId){
        GetUserRes getUserRes = userService.findUserInfo(userId);
        return new BaseResponse<>(getUserRes);
    }

//    @GetMapping("/member/{reviewId}")
//    public BaseResponse<MemberReview> getMember(@PathVariable("reviewId") long reviewId){
//        MemberReview member = userService.findMemberReview(reviewId);
//        return new BaseResponse<>(member);
//    }

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 메서드")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @DeleteMapping("/{userId}")
    public BaseResponse<String> deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return new BaseResponse<>();
    }
}
