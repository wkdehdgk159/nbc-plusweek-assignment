package com.example.nbcplusweekassignment.global.exception.jwt;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class EmptyClaimsJWTException extends CustomException {

    public EmptyClaimsJWTException() {
        super(ErrorCode.EMPTY_CLAIMS_JWT_EXCEPTION);
    }
}
