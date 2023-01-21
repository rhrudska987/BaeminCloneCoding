package com.example.demo.src.heart;

import com.example.demo.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Transactional
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
    public void createHeart(int userId, int storeId) throws BaseException {
        try{
            int heartId = heartDao.createHeart(userId, storeId);
            if(heartId == 0){
                throw new BaseException(CREATE_FAIL_HEART);
            }
        } catch (Exception exception) {
            logger.error("App - createHeart Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void doHeart(int userId, int storeId) throws BaseException{
        try{
            int result = heartDao.doHeart(userId, storeId);
            if(result == 0){
                throw new BaseException(CREATE_FAIL_HEART);
            }
        }catch (Exception exception){
            logger.error("App - doHeart Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void cancelHeart(int userId, int storeId) throws BaseException {
        try{
            int result = heartDao.cancelHeart(userId, storeId);
            if(result == 0){
                throw new BaseException(CANCEL_FAIL_HEART);
            }
        } catch(Exception exception){
            logger.error("App - cancelHeart Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
