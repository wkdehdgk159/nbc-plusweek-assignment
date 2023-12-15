package com.example.nbcplusweekassignment.global.exception.user;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class NotFoundUserException extends CustomException {

    public NotFoundUserException() {
        super(ErrorCode.NOT_FOUND_USER_EXCEPTION);
    }
}
