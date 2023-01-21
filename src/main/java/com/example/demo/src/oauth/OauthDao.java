package com.example.demo.src.oauth;

import com.example.demo.src.oauth.model.PostKakaoCreateUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class OauthDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int getUserId(String email){
        String getUserIdQuery = "select userId from User where email = ?";
        return this.jdbcTemplate.queryForObject(getUserIdQuery,
                int.class,
                email);
    }

    public int createUser(String nickName, String email){
        String createUserQuery = "insert into User (userName, email) values (?,?)";
        Object[] createUserParams = new Object[]{nickName, email};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int registerUser(PostKakaoCreateUserReq postreq, int userId){
        String registerUserInfoQuery = "update User set phoneNumber = ?, mailReceive = ?, SMSReceive = ?, contract1 = ?, contract2 = ?, contract3 = ?, contract4 = ?, contract5 = ? where userId = ?";
        Object[] registerUserInfoParams = new Object[]{postreq.getPhoneNumber(), postreq.getMailReceive(), postreq.getSMSReceive(), postreq.getContract1(), postreq.getContract2(), postreq.getContract3(), postreq.getContract4(),postreq.getContract5(), userId};
        return this.jdbcTemplate.update(registerUserInfoQuery, registerUserInfoParams);
    }

}
