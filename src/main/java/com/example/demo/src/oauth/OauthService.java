package com.example.demo.src.oauth;

import com.example.demo.config.BaseException;
import com.example.demo.src.oauth.model.PostKakaoCreateUserReq;
import com.example.demo.src.oauth.model.PostKakaoUserRes;
import com.example.demo.src.oauth.model.PostNaverUserRes;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

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
            sb.append("&client_id={REST_API}");
            sb.append("&redirect_uri=http://localhost:9000/oauth/kakao/callback");
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

    public PostKakaoUserRes GetKakaoUserInfo(String accessToken) {
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
                PostKakaoUserRes postKakaoUserRes = oauthProvider.loginKakao(email);
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

    public String getNaverAccessToken(String code){
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("&client_id={REST_API}");
            sb.append("&client_secret={SECRET}");
            sb.append("&redirect_uri=http://localhost:9000/oauth/naver/callback");
            sb.append("&code=" + code);
            String state = UUID.randomUUID().toString();
            sb.append("&state=" + state);

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
    public PostNaverUserRes GetNaverUserInfo(String accessToken) {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqUrl = "https://openapi.naver.com/v1/nid/me";
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
            JSONObject properties = (JSONObject) object.get("response");

            String id = (String) properties.get("id");
            String name = (String) properties.get("name");
            name = uniToKor(name);
            String email = (String) properties.get("email");

            System.out.println("email = " + email);
            System.out.println("name = " + name);
            userInfo.put("name", name);
            userInfo.put("email", email);

            int checkEmail = oauthDao.checkEmail(email);
            if(checkEmail == 1){
                PostNaverUserRes postNaverUserRes = oauthProvider.loginNaver(email);
                return postNaverUserRes;
            }
            else{
                int userId = oauthDao.createUser(name, email);
                return new PostNaverUserRes(userId, "");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String uniToKor(String uni){
        StringBuffer result = new StringBuffer();
        for(int i=0; i<uni.length(); i++){
            if(uni.charAt(i) == '\\' &&  uni.charAt(i+1) == 'u'){
                Character c = (char)Integer.parseInt(uni.substring(i+2, i+6), 16);
                result.append(c);
                i+=5;
            }else{
                result.append(uni.charAt(i));
            }
        }
        return result.toString();
    }

}
