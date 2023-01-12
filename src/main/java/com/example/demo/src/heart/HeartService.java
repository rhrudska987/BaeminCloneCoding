package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import com.example.demo.src.heart.model.PatchHeartReq;
import com.example.demo.src.heart.model.PostHeartReq;
import com.example.demo.src.heart.model.PostHeartRes;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.OrdersProvider;
import com.example.demo.src.orders.model.PatchOrderReq;
import com.example.demo.src.orders.model.PostOrderReq;
import com.example.demo.src.orders.model.PostOrderRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class HeartService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HeartDao heartDao;
    private final HeartProvider heartProvider;

    @Autowired
    HeartService(HeartDao heartDao, HeartProvider heartProvider){
        this.heartDao = heartDao;
        this.heartProvider = heartProvider;
    }

    //POST
    public PostHeartRes createHeart(int userId, int storeId) throws BaseException {
        try{
            int heartId = heartDao.createHeart(userId, storeId);
            return new PostHeartRes(heartId);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelHeart(PatchHeartReq patchHeartReq) throws BaseException {
        try{
            int result = heartDao.cancelHeart(patchHeartReq);
            if(result == 0){
                throw new BaseException(CANCEL_FAIL_HEART);
            }
        } catch(Exception exception){
            logger.error("App - leaveUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
