package com.example.nbcplusweekassignment.global.exception.comment;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class NotFoundCommentException extends CustomException {

    public NotFoundCommentException() {
        super(ErrorCode.NOT_FOUND_COMMENT_EXCEPTION);
    }
}
