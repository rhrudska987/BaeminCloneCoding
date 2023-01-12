package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.heart.model.*;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.OrdersService;
import com.example.demo.src.orders.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heart")
public class HeartController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HeartProvider heartProvider;
    @Autowired
    private final HeartService heartService;

    public HeartController(HeartProvider heartProvider, HeartService heartService){
        this.heartProvider = heartProvider;
        this.heartService = heartService;
    }

    /**
     * 찜 하기 API
     * [PATCH] /heart/users/:userId/stores/{storeId}
     * @return BaseResponse<PostHeartRes>
     */
    @ResponseBody
    @PostMapping("/users/{userId}/stores/{storeId}")
    public BaseResponse<PostHeartRes> createHeart(@PathVariable("userId") int userId, @PathVariable("storeId") int storeId/*, @RequestBody PostHeartReq postHeartReq*/) {
        try{
            /*postHeartReq.setUserId(userId);*/
            PostHeartRes postHeartRes = heartService.createHeart(userId, storeId);
            return new BaseResponse<>(postHeartRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 찜 해제 API
     * [PATCH] /heart/:heartId/users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{heartId}/users/{userId}")
    public BaseResponse<String> cancelHeart(@PathVariable("heartId") int heartId, @PathVariable("userId") int userId, @RequestBody Heart heart){
        try{
            PatchHeartReq patchHeartReq = new PatchHeartReq(heartId, userId, heart.getStatus());
            heartService.cancelHeart(patchHeartReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
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
            List<GetHeartRes> getHeartRes = heartProvider.getHeart(userId, status);
            return new BaseResponse<>(getHeartRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
