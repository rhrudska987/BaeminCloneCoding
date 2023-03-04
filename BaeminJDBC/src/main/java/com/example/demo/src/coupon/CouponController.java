package com.example.demo.src.coupon;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.coupon.model.GetCouponRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/coupon")
public class CouponController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CouponProvider couponProvider;
    @Autowired
    private final JwtService jwtService;

    public CouponController(CouponProvider couponProvider, JwtService jwtService){
        this.couponProvider = couponProvider;
        this.jwtService = jwtService;
    }

    /**
     * 쿠폰목록 조회 API
     * [GET] /heart/users/:userId?status=N
     * @return BaseResponse<List<GetHeartRes>>
     */
    @ResponseBody
    @GetMapping("/users/{userId}") // (GET) 127.0.0.1:9000/coupon/users/:userid?status=N
    public BaseResponse<List<GetCouponRes>> getCoupon(@PathVariable("userId") int userId, @RequestParam("status") String status) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetCouponRes> getCouponRes = couponProvider.getCoupon(userId, status);
            return new BaseResponse<>(getCouponRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
