package com.mbtree.mbtree.config;
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

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),


    //message
    MESSAGES_EMPTY_POST_ID(false, 2020,"유효하지 않은 MessageId 입니다."),
    MESSAGES_EMPTY_USER_MESSAGES(false, 2021,"user의 Message가 없습니다"),


    //paper
    PAPER_ALREADY_BUY(false, 2030, "이미 구입한 편지지입니다"),

    //point
    POINT_LACK(false, 2040, "포인트가 부족합니다"),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),



    MODIFY_FAIL_MYPAGE(false,4015,"마이페이지 수정 실패"),//아직안만ㄷ름
    DELETE_FAIL_POST(false,4016,"게시글 삭제 실패");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}