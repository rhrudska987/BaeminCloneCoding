package baemin.baeminjpa.dto;

import baemin.baeminjpa.domain.ReceiveStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Schema(description = "PostUser DTO")
public class PostUserDto {
    @NotBlank(message = "회원 이름은 필수입니다.")
    @Schema(description = "유저 이름")
    private String userName;
    @Schema(description = "유저 전화번호")
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNumber;
    @Schema(description = "유저 이메일")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식을 지켜주세요.")
    private String email;
    @NotBlank(message = "아이디를 입력해주세요.")
    @Schema(description = "유저 ID")
    private String loginId;

    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+!=])(?=\\S+$).{8,20}"
            , message = "패스워드는 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 하며 최소 8자리여야 하며 최대 20자리까지 가능합니다.")
    @Schema(description = "유저 비밀번호")
    private String password;

    @Schema(description = "메일 수신 여부")
    private ReceiveStatus mailReceive;
    @Schema(description = "문자 수신 여부")
    private ReceiveStatus SMSReceive;
}
