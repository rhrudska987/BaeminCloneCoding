package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexAddress;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     *
     * @return BaseResponse<List < GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try {
            if (Email == null) {
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}") // (GET) 127.0.0.1:9000/user/:userid
    public BaseResponse<GetUserRes> getUser(@PathVariable("userId") int userId) {
        // Get Users
        try{
            GetUserRes getUserRes = userProvider.getUser(userId);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 회원 알림 조회 API (알림센터)
     * [GET] /users/:userId/notice
     * @return BaseResponse<GetNoticeRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}/notice") // (GET) 127.0.0.1:9000/users/:userid/notice
    public BaseResponse<List<GetNoticeRes>> getUserNotice(@PathVariable("userId") int userId) {
        // Get Users
        try{
            List<GetNoticeRes> getNoticeRes = userProvider.getUserNotice(userId);
            return new BaseResponse<>(getNoticeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 회원 주소 조회 API (주소목록)
     * [GET] /users/:userId/address
     * @return BaseResponse<GetAddressRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userId}/address") // (GET) 127.0.0.1:9000/users/:userid/address
    public BaseResponse<List<GetAddressRes>> getUserAddress(@PathVariable("userId") int userId) {
        // Get Users
        try{
            List<GetAddressRes> getAddressRes = userProvider.getUserAddress(userId);
            return new BaseResponse<>(getAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    /*@ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/

    /**
     * 주소추가 API
     * [POST] /users
     * @return BaseResponse<PostAddressRes>
     */
    @ResponseBody
    @PostMapping("/{userId}/address")
    public BaseResponse<PostAddressRes> createAddress(@PathVariable("userId") int userId, @RequestBody PostAddressReq postAddressReq) {
        if(postAddressReq.getAddress() == null){
            return new BaseResponse<>(POST_ADDRESS_EMPTY_EMAIL);
        }
        //주소 정규표현
        if(!isRegexAddress(postAddressReq.getAddress())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostAddressRes postAddressRes = userService.createAddress(postAddressReq, userId);
            return new BaseResponse<>(postAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 주소 삭제 API
     * [DELETE] /users/:userId/address/:addressId
     * @return BaseResponse<DelAddressRes>
     */
    @ResponseBody
    @DeleteMapping("/{userId}/address/{addressId}")
    public BaseResponse<DelAddressRes> deleteAddress(@PathVariable("userId") int userId, @PathVariable("addressId") int addressId){
        // delete address
        try{
            int result = userService.delUserAddress(userId, addressId);
            if(result == 0){
                return new BaseResponse<>(DELETE_FAIL_ADDRESS);
            }
            DelAddressRes delAddressRes = new DelAddressRes(userId, addressId);
            return new BaseResponse<>(delAddressRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    /*@ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }*/

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserInfo(@PathVariable("userId") int userId, @RequestBody User user){
        try {
            GetUserRes getUserRes = userProvider.getUser(userId);
            Object[] objects = new Object[5];
            if(user.getProfileImage() == null) objects[0] = getUserRes.getProfileImage();
            else objects[0] = user.getProfileImage();
            if(user.getUserName() == null) objects[1] = getUserRes.getUserName();
            else objects[1] = user.getUserName();
            if(user.getPhoneNumber() == null) objects[2] = getUserRes.getPhoneNumber();
            else objects[2] = user.getPhoneNumber();
            if(user.getMailReceive() == null) objects[3] = getUserRes.getMailReceive();
            else objects[3] = user.getMailReceive();
            if(user.getSMSReceive() == null) objects[4] = getUserRes.getSMSReceive();
            else objects[4] = user.getSMSReceive();
            PatchUserReq patchUserReq = new PatchUserReq(userId, (String)objects[0], (String)objects[1], (String)objects[2], (String)objects[3], (String)objects[4]);
            userService.modifyUserInfo(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 탈퇴 API
     * [PATCH] /users/:userId/withdrawal
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userId}/withdrawal")
    public BaseResponse<String> leaveUser(@PathVariable("userId") int userId, @RequestBody User user){
        try{
            PatchUserReq patchUserReq = new PatchUserReq(userId, user.getProfileImage(),user.getUserName(), user.getPhoneNumber(), user.getMailReceive(), user.getSMSReceive(), user.getUserStatus());
            if(patchUserReq.getUserStatus().equals("Y")){
                return new BaseResponse<>(LEAVE_USER);
            }
            userService.leaveUser(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /*@ResponseBody
    @PatchMapping("/{userId}")
    public BaseResponse<String> modifyUserName(@PathVariable("userId") int userId, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(user.getProfileImage(),user.getUserName(), user.getPassword(), user.getPhoneNumber(), user.getMailReceive(), user.getSMSReceive());
            userService.modifyUserInfo(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }*/

}
