package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String userName;
    private String phoneNumber;
    private String email;
    private String password;
    private String mailReceive;
    private String SMSReceive;
    private String baeminTos;
    private String financialTransactionTos;
    private String personalInfoTosEssential;
    private String personalInfoTosChoice;
    private String personalInfoTos3Party;
}
