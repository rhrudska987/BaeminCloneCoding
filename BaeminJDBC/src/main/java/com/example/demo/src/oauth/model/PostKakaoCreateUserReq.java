package com.example.demo.src.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostKakaoCreateUserReq {
    private String phoneNumber;
    private String contract1;
    private String contract2;
    private String contract3;
    private String contract4;
    private String contract5;
    private String mailReceive;
    private String SMSReceive;
}
