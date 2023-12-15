package com.example.nbcplusweekassignment.global.exception.user;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class InvalidUserAndPasswordException extends CustomException {

    public InvalidUserAndPasswordException() {
        super(ErrorCode.INVALID_USER_ID_AND_PASSWORD_EXCEPTION);
    }
}
