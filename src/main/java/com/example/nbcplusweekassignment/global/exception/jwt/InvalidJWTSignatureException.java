package com.example.nbcplusweekassignment.global.exception.jwt;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class InvalidJWTSignatureException extends CustomException {

    public InvalidJWTSignatureException() {
        super(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION);
    }
}
