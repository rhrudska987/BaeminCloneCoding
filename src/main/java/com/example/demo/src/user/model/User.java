package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String profileImage;
    private String grade;
    private double point;
    private String userStatus;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String selectedAddress;
    private String phoneNumber;
    private String email;
    private String loginId;
    private String password;
    private int totalReview;
    private double avgReview;
    private int fivepoint;
    private int fourpoint;
    private int threepoint;
    private int twooint;
    private int onepoint;
    private String mailReceive;
    private String SMSReceive;
}
