package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import com.example.demo.src.heart.model.GetHeartRes;
import com.example.demo.src.orders.OrdersDao;
import com.example.demo.src.orders.model.GetOrderRes;
import com.example.demo.src.user.model.GetUserRes;
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
public class HeartProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HeartDao heartDao;

    @Autowired
    public HeartProvider(HeartDao heartDao){
        this.heartDao = heartDao;
    }

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
            logger.error("App - getHeart Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetHeartRes getHeartRes(int userId, int storeId) throws BaseException{
        try {
            GetHeartRes getHeartRes = heartDao.getHeart(userId, storeId);
            return getHeartRes;
        } catch (Exception exception) {
            logger.error("App - getHeartRes Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkHeart(int userId, int storeId) throws BaseException{
        try{
            return heartDao.checkHeart(userId, storeId);
        } catch (Exception exception){
            logger.error("App - checkHeart Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
