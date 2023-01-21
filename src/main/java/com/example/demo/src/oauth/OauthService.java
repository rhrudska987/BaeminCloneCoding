package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.oauth.model.PostKakaoCreateUserReq;
import com.example.demo.src.oauth.model.PostKakaoCreateUserRes;
import com.example.demo.src.oauth.model.PostKakaoUserRes;
import com.example.demo.src.user.UserController;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.PostUserRes;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_USERINFO;

@Service
@Transactional(rollbackFor = Exception.class)
public class OauthService {

    @Autowired
    private final OauthDao oauthDao;
    @Autowired
    private final OauthProvider oauthProvider;

    public OauthService(OauthDao oauthDao, OauthProvider oauthProvider){
        this.oauthDao = oauthDao;
        this.oauthProvider = oauthProvider;
    }

    public String getKakaoAccessToken(String code){
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=3277661e7e2b4dd852005604bbd81481");
            sb.append("&redirect_uri=http://localhost:9000/oauth/kakao");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            System.out.println(sb.toString());
            int responseCode = connection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("responseBody = " + result);

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(result);

            access_Token = (String) object.get("access_token");
            refresh_Token = (String) object.get("refresh_token");
            System.out.println("access_Token = " + access_Token);
            System.out.println("refresh_Token = " + refresh_Token);

            br.close();
            bw.close();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    /*public void createKakaoUser(String token) {
        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + token);

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("responseBody = " + result);

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(result);
            JSONObject properties = (JSONObject) object.get("properties");
            JSONObject kakao_account = (JSONObject) object.get("kakao_account");

            Long id = (Long) object.get("id");
            String nickName = (String) properties.get("nickname");
            String email = (String) kakao_account.get("email");

            *//*String kakao_account = (String) object.get("kakao_account");*//*
            *//*boolean hasEmail = (boolean) object.get("has_email");
            System.out.println("hasEmail = " + hasEmail);*//*

            System.out.println("id : " + id);
            System.out.println("nickName = " + nickName);
            System.out.println("email = " + email);

            *//*int checkEmail = oauthDao.checkEmail(email);
            if(checkEmail == 1){
                PostLoginRes postLoginRes = oauthProvider.login(email);
                return postLoginRes;
            }
            else{
                try{
                    int userId = oauthDao.createUser(postUserReq);

                }
            }*//*

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void createUser(PostKakaoCreateUserReq postKakaoCreateUserReq, int userId) throws BaseException {
        try {
            int result = oauthDao.registerUser(postKakaoCreateUserReq, userId);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERINFO);
            }
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostKakaoUserRes GetUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = connection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("responseBody = " + result);

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(result);
            JSONObject properties = (JSONObject) object.get("properties");
            JSONObject kakao_account = (JSONObject) object.get("kakao_account");

            Long id = (Long) object.get("id");
            String nickName = (String) properties.get("nickname");
            String email = (String) kakao_account.get("email");
            userInfo.put("nickName", nickName);
            userInfo.put("email", email);

            int checkEmail = oauthDao.checkEmail(email);
            if(checkEmail == 1){
                PostKakaoUserRes postKakaoUserRes = oauthProvider.login(email);
                return postKakaoUserRes;
            }
            else{
                int userId = oauthDao.createUser(nickName, email);
                return new PostKakaoUserRes(userId, "");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
