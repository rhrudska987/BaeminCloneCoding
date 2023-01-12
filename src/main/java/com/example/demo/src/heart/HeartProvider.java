package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.model.GetOrderRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class HeartProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HeartDao heartDao;

    @Autowired
    public HeartProvider(HeartDao heartDao){
        this.heartDao = heartDao;
    }

    /*public int checkCanceled(int orderId) throws BaseException {
        try{
            return ordersDao.checkCanceled(orderId);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }*/

    public List<GetHeartRes> getHeart(int userId, String status) throws BaseException {
        try{
            List<GetHeartRes> getHeartRes = heartDao.getHeartRes(userId, status);
            for(int i=0; i<getHeartRes.size(); i++) {
                if (getHeartRes.get(i).getStatus().equals("O"))
                    getHeartRes.get(i).setStatus("OPEN");
                else
                    getHeartRes.get(i).setStatus("CLOSE");
            }
            return getHeartRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
