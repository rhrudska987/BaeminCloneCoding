package baemin.baeminjpa.dto;

import baemin.baeminjpa.config.BaseResponse;
import baemin.baeminjpa.config.BaseResponseStatus;
import baemin.baeminjpa.domain.ReceiveStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

import static baemin.baeminjpa.config.BaseResponseStatus.POST_USERS_EMPTY_USERNAME;

@Getter
@Setter
public class PostUserDto {
    @NotBlank(message = "회원 이름은 필수입니다.")
    private String userName;
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;
    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}"
            , message = "패스워드는 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 하며 최소 8자리여야 하며 최대 20자리까지 가능합니다.")
    private String password;

    private ReceiveStatus mailReceive;
    private ReceiveStatus SMSReceive;
}
