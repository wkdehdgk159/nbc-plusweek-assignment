package com.example.nbcplusweekassignment.global.exception.jwt;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class UnsupportedJWTException extends CustomException {

    public UnsupportedJWTException() {
        super(ErrorCode.UNSUPPORTED_JWT_EXCEPTION);
    }
}
