package com.example.nbcplusweekassignment.global.exception.jwt;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class ExpiredJWTException extends CustomException {

    public ExpiredJWTException() {
        super(ErrorCode.EXPIRED_JWT_EXCEPTION);
    }
}
