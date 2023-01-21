package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.heart.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/heart")
public class HeartController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HeartProvider heartProvider;
    @Autowired
    private final HeartService heartService;
    @Autowired
    private final JwtService jwtService;

    public HeartController(HeartProvider heartProvider, HeartService heartService, JwtService jwtService){
        this.heartProvider = heartProvider;
        this.heartService = heartService;
        this.jwtService = jwtService;
    }

    /**
     * 찜 API
     * [PATCH] /heart/users/:userId/stores/{storeId}
     * @return BaseResponse<PostHeartRes>
     */
    @ResponseBody
    @PostMapping("/users/{userId}/stores/{storeId}")
    public BaseResponse<String> createHeart(@PathVariable("userId") int userId, @PathVariable("storeId") int storeId) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            int checkHeart = heartProvider.checkHeart(userId, storeId);
            if(checkHeart == 0)
                heartService.createHeart(userId, storeId);
            else{
                GetHeartRes getHeartRes = heartProvider.getHeartRes(userId, storeId);
                if(getHeartRes.getStatus().equals("N"))
                    heartService.cancelHeart(userId, storeId);
                else if (getHeartRes.getStatus().equals("Y")) {
                    heartService.doHeart(userId, storeId);
                }
                else{
                    return new BaseResponse<>(DATABASE_ERROR);
                }
            }
            return new BaseResponse<>();
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜목록 조회 API
     * [GET] /heart/users/:userId?status=N
     * @return BaseResponse<List<GetHeartRes>>
     */
    @ResponseBody
    @GetMapping("/users/{userId}") // (GET) 127.0.0.1:9000/heart/users/:userid?status=N
    public BaseResponse<List<GetHeartRes>> getHeart(@PathVariable("userId") int userId, @RequestParam("status") String status) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetHeartRes> getHeartRes = heartProvider.getHeart(userId, status);
            return new BaseResponse<>(getHeartRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
