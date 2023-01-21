package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private String userName;
    private String profileImage;
    private String grade;
    private String password;
    private String phoneNumber;
    private String userStatus;
    private String email;
    private String mailReceive;
    private String SMSReceive;

    public GetUserRes(String userName, String profileImage, String grade, String phoneNumber, String userStatus, String email, String mailReceive, String SMSReceive) {
        this.userName = userName;
        this.profileImage = profileImage;
        this.grade = grade;
        this.phoneNumber = phoneNumber;
        this.userStatus = userStatus;
        this.email = email;
        this.mailReceive = mailReceive;
        this.SMSReceive = SMSReceive;
    }
}
