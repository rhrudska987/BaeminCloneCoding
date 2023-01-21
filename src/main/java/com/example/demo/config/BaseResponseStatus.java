package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    LEAVE_USER(false, 2004, "이미 탈퇴한 유저입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EMPTY_USERNAME(false, 2017, "닉네임를 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2018, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2019, "비밀번호 형식을 확인해주세요."),
    NOT_ACCEPT_CONTRACT1(false, 2020, "배달의민족 이용약관에 동의하셔야 합니다."),
    NOT_ACCEPT_CONTRACT2(false, 2021, "전자금융거래 이용약관에 동의하셔야 합니다."),
    NOT_ACCEPT_CONTRACT3(false, 2022, "개인정보 수집 이용에 동의하셔야 합니다."),
    POST_ADDRESS_EMPTY_EMAIL(false, 2019, "주소를 입력해주세요."),
    POST_ADDRESS_INVALID_EMAIL(false, 2020, "주소 형식을 확인해주세요."),
    POST_REVIEW_EMPTY_COMMENT(false, 2021, "코멘트를 입력해주세요."),
    POST_REVIEW_LENGTH_OVER_COMMENT(false, 2022, "코멘트는 30자를 넘길 수 없습니다."),
    POST_ADDRESS_EMPTY_STATUS(false, 2023, "사용여부를 체크해주세요."),
    POST_STORE_EMPTY_STORENAME(false, 2024, "가게 이름을 입력해주세요."),
    POST_STORE_EMPTY_TELEPHONENUMBER(false, 2025, "전화번호를 입력해주세요."),
    POST_STORE_EMPTY_ADDRESS(false, 2026, "주소를 입력해주세요."),
    POST_STORE_EMPTY_STOREIMAGE(false, 2027, "대표 이미지를 넣어주세요."),
    PATCH_STORE_EMPTY_STORENAME(false, 2028, "가게이름을 입력해주세요."),
    PATCH_STORE_EMPTY_ADDRESS(false, 2028, "주소를 입력해주세요."),
    POST_STORE_EMPTY_STOREMENU(false, 2029, "메뉴이름을 입력해주세요."),
    POST_STORE_EMPTY_MENUPRICE(false, 2030, "메뉴가격을 입력해주세요."),
    POST_STORE_INVALID_MENUPRICE(false, 2031, "가격에는 숫자 이외의 다른 문자는 들어갈 수 없습니다."),
    GET_STORE_EMPTY_KEYWORD(false, 2032, "검색어를 입력해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    INVALID_USER_JWT(false,3001,"권한이 없는 유저의 접근입니다."),
    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    DUPLICATED_PASSWORD(false, 3014, "같인 비밀번호입니다."),
    FAILED_TO_LOGIN(false,3015,"없는 이메일이거나 비밀번호가 틀렸습니다."),
    FAILED_TO_SEARCH(false, 3016, "딱 맞는 결과가 없습니다."),




    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    CANCELED_ORDER(false, 4013, "이미 취소된 주문입니다."),
    MODIFY_FAIL_USERINFO(false,4014,"유저정보 수정 실패"),
    DELETE_FAIL_ADDRESS(false, 4015, "주소내역 삭제 실패"),
    CANCEL_FAIL_ORDER(false, 4016, "주문취소 실패"),
    CANCEL_FAIL_REVIEW(false, 4017, "리뷰삭제 실패"),
    CANCEL_FAIL_HEART(false, 4018, "찜 해제 실패"),
    CREATE_FAIL_HEART(false, 4019, "짐 하기 실패"),
    MODIFY_FAIL_STOREINFO(false, 4020, "가게정보 수정 실패"),
    MODIFY_FAIL_STOREMENUINFO(false, 4021, "메뉴 정보 수정 실패"),
    CANCEL_FAIL_STORE(false, 4021, "가게삭제 실패"),
    REMOVE_FAIL_STOREMENU(false, 4022, "메뉴 삭제 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
