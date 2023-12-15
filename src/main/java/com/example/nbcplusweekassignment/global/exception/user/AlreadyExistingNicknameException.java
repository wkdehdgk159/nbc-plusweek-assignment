package com.example.nbcplusweekassignment.global.exception.user;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class AlreadyExistingNicknameException extends CustomException {

    public AlreadyExistingNicknameException() {
        super(ErrorCode.ALREADY_EXIST_NICKNAME_EXCEPTION);
    }
}
