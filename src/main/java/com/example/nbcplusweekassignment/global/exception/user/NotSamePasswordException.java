package com.example.nbcplusweekassignment.global.exception.user;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class NotSamePasswordException extends CustomException {

    public NotSamePasswordException() {
        super(ErrorCode.NOT_SAME_PASSWORD_EXCEPTION);
    }
}
