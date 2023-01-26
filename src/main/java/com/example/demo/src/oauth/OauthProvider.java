package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.src.oauth.model.PostKakaoUserRes;
import com.example.demo.src.oauth.model.PostNaverUserRes;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class OauthProvider {

    private final JwtService jwtService;
    private final OauthDao oauthDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OauthProvider(OauthDao oauthDao, JwtService jwtService){
        this.oauthDao = oauthDao;
        this.jwtService = jwtService;
    }

    public PostKakaoUserRes loginKakao(String email) throws BaseException{
        int userId = oauthDao.getUserId(email);
        String jwt = jwtService.createJwt(userId);
        return new PostKakaoUserRes(userId, jwt);
    }

    public PostNaverUserRes loginNaver(String email) throws BaseException{
        int userId = oauthDao.getUserId(email);
        String jwt = jwtService.createJwt(userId);
        return new PostNaverUserRes(userId, jwt);
    }

}
