package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserReq {
    private int userId;
    private String profileImage;
    private String userName;
    private String phoneNumber;
    private String mailReceive;
    private String SMSReceive;
    private String userStatus;

    public PatchUserReq(int userId, String profileImage, String userName, String phoneNumber, String mailReceive, String SMSReceive) {
        this.userId = userId;
        this.profileImage = profileImage;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.mailReceive = mailReceive;
        this.SMSReceive = SMSReceive;
    }
}
