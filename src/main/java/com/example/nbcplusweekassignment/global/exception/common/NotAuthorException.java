package com.example.nbcplusweekassignment.global.exception.common;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class NotAuthorException extends CustomException {

    public NotAuthorException() {
        super(ErrorCode.NOT_AUTHOR_EXCEPTION);
    }
}
