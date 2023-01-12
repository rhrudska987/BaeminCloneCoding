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
    private String phoneNumber;
    private String email;
    private String mailReceive;
    private String SMSReceive;
}
