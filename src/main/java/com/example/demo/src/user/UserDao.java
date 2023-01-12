package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getString("userName"),
                        rs.getString("profileImage"),
                        rs.getString("grade"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("mailReceive"),
                        rs.getString("SMSReceive"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("userName"),
                        rs.getString("profileImage"),
                        rs.getString("grade"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("mailReceive"),
                        rs.getString("SMSReceive")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userId){
        String getUserQuery = "select * from User where userId = ?";
        int getUserParams = userId;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getString("userName"),
                        rs.getString("profileImage"),
                        rs.getString("grade"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("mailReceive"),
                        rs.getString("SMSReceive")),
                getUserParams);
    }

    public List<GetNoticeRes> getNoticeRes(int userId){
        String getUserNoticeQuery = "select noticeTitle, noticeContent, createAt from Notice where Notice.userId = ?";
        int getUserNoticeParams = userId;
        return this.jdbcTemplate.query(getUserNoticeQuery,
                (rs,rowNum) -> new GetNoticeRes(
                        rs.getString("noticeTitle"),
                        rs.getString("noticeContent"),
                        rs.getString("createAt")),
                getUserNoticeParams);
    }

    public List<GetAddressRes> getAddressRes(int userId){
        String getUserAddressQuery = "select address, status from Address where Address.userId = ?";
        int getUserAddressParams = userId;
        return this.jdbcTemplate.query(getUserAddressQuery,
                (rs,rowNum) -> new GetAddressRes(
                        rs.getString("address"),
                        rs.getString("status")),
                getUserAddressParams);
    }

    public int createAddress(PostAddressReq postAddressReq, int userId){
        String createAddressQuery = "insert into Address (address, status, userId) VALUES (?,?,?)";
        Object[] createAddressParams = new Object[]{postAddressReq.getAddress(), postAddressReq.getStatus(), userId};
        this.jdbcTemplate.update(createAddressQuery, createAddressParams);

        String lastInserIdQuery = "select last_insert_id()"; //last_insert_id : 테이블의 마지막 autoIncrement 값을 리턴
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int delUserAddress(int userId, int addressId){
        String delUserAddressQuery = "delete from Address where userId = ? and addressId = ?;";
        int userIdParam = userId;
        int addressIdParam = addressId;
        return this.jdbcTemplate.update(delUserAddressQuery, userIdParam, addressIdParam);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into UserInfo (userName, ID, password, email) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getLoginId(), postUserReq.getPassword(), postUserReq.getEmail()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    /*public int modifyUserInfo(PatchUserReq patchUserReq){
        String modifyUserInfoQuery = "update User set profileImage = ?, userName = ?, phoneNumber = ?, mailReceive = ?, SMSReceive = ? where userId = ? ";
        Object[] modifyUserInfoParams = new Object[]{patchUserReq.getProfileImage(), patchUserReq.getUserName(), patchUserReq.getPhoneNumber(), patchUserReq.getMailReceive(), patchUserReq.getSMSReceive(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);
    }*/

    public int modifyUserInfo(PatchUserReq patchUserReq){
        String modifyUserInfoQuery = "update User set profileImage = ?, userName = ?, phoneNumber = ?, mailReceive = ?, SMSReceive = ? where userId = ?";
        Object[] modifyUserInfoParams = new Object[]{patchUserReq.getProfileImage(), patchUserReq.getUserName(), patchUserReq.getPhoneNumber(), patchUserReq.getMailReceive(), patchUserReq.getSMSReceive(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);
    }

    public int leaveUser(PatchUserReq patchUserReq){
        String leaveUserQuery = "update User set userStatus = ? where userId = ?";
        Object[] leaveUserParams = new Object[]{patchUserReq.getUserStatus(), patchUserReq.getUserId()};
        return this.jdbcTemplate.update(leaveUserQuery, leaveUserParams);
    }

    /*public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserId()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }*/

    /*public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, password,email,userName,ID from UserInfo where ID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("ID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("email")
                ),
                getPwdParams
                );

    }*/


}
