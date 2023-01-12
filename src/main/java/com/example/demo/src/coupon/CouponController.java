package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.src.heart.HeartProvider;
import com.example.demo.src.heart.HeartService;
import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.heart.model.Heart;
import com.example.demo.src.heart.model.PatchHeartReq;
import com.example.demo.src.heart.model.PostHeartRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CouponProvider couponProvider;

    public CouponController(CouponProvider couponProvider){
        this.couponProvider = couponProvider;
    }

    /**
     * 찜목록 조회 API
     * [GET] /heart/users/:userId?status=N
     * @return BaseResponse<List<GetHeartRes>>
     */
    @ResponseBody
    @GetMapping("/users/{userId}") // (GET) 127.0.0.1:9000/coupon/users/:userid?status=N
    public BaseResponse<List<GetCouponRes>> getCoupon(@PathVariable("userId") int userId, @RequestParam("status") String status) {
        try{
            List<GetCouponRes> getCouponRes = couponProvider.getCoupon(userId, status);
            return new BaseResponse<>(getCouponRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
