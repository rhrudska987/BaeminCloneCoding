package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
@Transactional(readOnly = true)
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException {
        try {
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        } catch (Exception exception) {
            logger.error("App - getUsersByEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUser(int userId) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userId);
            return getUserRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUser(String email) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(email);
            return getUserRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetNoticeRes> getUserNotice(int userId) throws BaseException {
        try{
            List<GetNoticeRes> getNoticeRes = userDao.getNoticeRes(userId);
            for(int i=0; i<getNoticeRes.size(); i++){
                Timestamp timestamp = Timestamp.valueOf(getNoticeRes.get(i).getCreateAt());
                LocalDateTime localDateTime = timestamp.toLocalDateTime();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM월 dd일 H:m");
                String nowString = localDateTime.format(dateTimeFormatter);
                getNoticeRes.get(i).setCreateAt(nowString);
            }
            return getNoticeRes;
        } catch (Exception exception) {
            logger.error("App - getNotice Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetAddressRes> getUserAddress(int userId) throws BaseException {
        try{
            List<GetAddressRes> getAddressRes = userDao.getAddressRes(userId);
            return getAddressRes;
        } catch (Exception exception) {
            logger.error("App - getAddress Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user;
        String encryptPwd;
        if(checkEmail(postLoginReq.getEmail()) == 0){
            throw new BaseException(FAILED_TO_LOGIN);
        }
        try {
            /*User*/ user = userDao.getPwd(postLoginReq);

            /*String encryptPwd;*/
            try {
                encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());
            } catch (Exception exception) {
                logger.error("App - logIn Provider Encrypt Error", exception);
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }

            /*if(user.getPassword().equals(encryptPwd)){
                int userId = user.getUserId();
                String jwt = jwtService.createJwt(userId);
                return new PostLoginRes(userId,jwt);
            }
            else{
                throw new BaseException(FAILED_TO_LOGIN);
            }*/
        } catch (Exception exception) {
            logger.error("App - logIn Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
        if(user.getPassword().equals(encryptPwd)){
            int userId = user.getUserId();
            String jwt = jwtService.createJwt(userId);
            return new PostLoginRes(userId,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }
}
