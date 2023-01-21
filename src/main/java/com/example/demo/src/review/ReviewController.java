package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.OrdersService;
import com.example.demo.src.orders.model.*;
import com.example.demo.src.review.model.GetUserReviewRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.user.model.PostAddressReq;
import com.example.demo.src.user.model.PostAddressRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexAddress;

@RestController
@RequestMapping("/review")
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 리뷰 삭제 API
     * [PATCH] /review/:reviewId/users/:userId
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{reviewId}/users/{userId}")
    public BaseResponse<String> cancelOrder(@PathVariable("reviewId") int reviewId, @PathVariable("userId") int userId){
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchReviewReq patchReviewReq = new PatchReviewReq(reviewId, userId);
            reviewService.cancelReview(patchReviewReq);
            return new BaseResponse<>();
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 유저 리뷰내역 조회 API
     * [GET] review/users/:userId
     * @return BaseResponse<List<GetUserReviewRes>>
     */
    @ResponseBody
    @GetMapping("users/{userId}") // (GET) 127.0.0.1:9000/review/users/:userid
    public BaseResponse<List<GetUserReviewRes>> getUserReview(@PathVariable("userId") int userId) {
        try{
            List<GetUserReviewRes> getUserReviewRes = reviewProvider.getUserReviewRes(userId);
            return new BaseResponse<>(getUserReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 리뷰작성 API
     * [POST] review/users/:userId/stores/:storeId
     * @return BaseResponse<PostAddressRes>
     */
    @ResponseBody
    @PostMapping("/users/{userId}/stores/{storeId}")
    public BaseResponse<PostReviewRes> createReview(@PathVariable("userId") int userId, @PathVariable("storeId") int storeId, @RequestBody PostReviewReq postReviewReq) {
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userId != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            if(postReviewReq.getComment() == null){
                return new BaseResponse<>(POST_REVIEW_EMPTY_COMMENT);
            }
            if(postReviewReq.getComment().length() > 30){
                return new BaseResponse<>(POST_REVIEW_LENGTH_OVER_COMMENT);
            }
            postReviewReq.setUserId(userId);
            postReviewReq.setStoreId(storeId);
            PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
