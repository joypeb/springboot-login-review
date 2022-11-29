package com.hospital.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorCode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "UserName이 중복되었습니다"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not_Found User Name"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "Invalid Password")
    ;

    private HttpStatus status;
    private String message;
}
