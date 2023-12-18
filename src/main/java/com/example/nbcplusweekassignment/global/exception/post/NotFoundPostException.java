package com.example.nbcplusweekassignment.global.exception.post;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class NotFoundPostException extends CustomException {

    public NotFoundPostException() {
        super(ErrorCode.NOT_FOUND_POST_EXCEPTION);
    }
}
