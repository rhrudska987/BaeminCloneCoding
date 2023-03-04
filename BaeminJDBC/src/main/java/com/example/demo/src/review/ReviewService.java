package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.orders.model.PostOrderRes;
import com.example.demo.src.review.model.PatchReviewReq;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;

    @Autowired
    ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider){
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
    }

    //POST
    public PostReviewRes createReview(PostReviewReq postReviewReq) throws BaseException {
        try{
            int reviewId = reviewDao.createReview(postReviewReq);
            return new PostReviewRes(reviewId);
        } catch (Exception exception) {
            logger.error("App - createReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelReview(PatchReviewReq patchReviewReq) throws BaseException {
        try{
            int result = reviewDao.cancelReview(patchReviewReq);
            if(result == 0){
                throw new BaseException(CANCEL_FAIL_REVIEW);
            }
        } catch(Exception exception){
            logger.error("App - cancelReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
