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

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(DUPLICATED_EMAIL);
        }
        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userId = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userId);
            return new PostUserRes(userId, jwt);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    public PostAddressRes createAddress(PostAddressReq postAddressReq, int userId) throws BaseException {
        try{
            int addressId = userDao.createAddress(postAddressReq, userId);
            return new PostAddressRes(addressId);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int delUserAddress(int userId, int addressId) throws BaseException {
        try {
            int result = userDao.delUserAddress(userId, addressId);
            return result;
        } catch(Exception exception){
            logger.error("App - deleteUserAddress Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void leaveUser(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.leaveUser(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
        } catch(Exception exception){
            logger.error("App - leaveUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserInfo(PatchUserReq patchUserReq) throws BaseException {
        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(patchUserReq.getPassword());
            patchUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int result = userDao.modifyUserInfo(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
        } catch(Exception exception){
            logger.error("App - modifyUserInfo Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /*public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }*/
}
