package baemin.baeminjpa.dto;

import baemin.baeminjpa.domain.ReceiveStatus;
import baemin.baeminjpa.domain.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private String userName;
    private String profileImage;
    private String grade;
    private String phoneNumber;
    private UserStatus status;
    private String email;
    private ReceiveStatus mailReceive;
    private ReceiveStatus SMSReceive;
}
