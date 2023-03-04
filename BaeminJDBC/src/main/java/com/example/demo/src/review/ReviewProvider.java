package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.review.model.GetUserReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@Transactional(readOnly = true)
public class ReviewProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewProvider(ReviewDao reviewDao){
        this.reviewDao = reviewDao;
    }

    public List<GetUserReviewRes> getUserReviewRes(int userId) throws BaseException {
        try{
            List<GetUserReviewRes> getUserReviewRes = reviewDao.getUserReviewRes(userId);
            return getUserReviewRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
