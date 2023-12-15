package com.example.nbcplusweekassignment.global.exception.user;

import com.example.nbcplusweekassignment.global.exception.CustomException;
import com.example.nbcplusweekassignment.global.exception.ErrorCode;

public class PasswordContainsNicknameException extends CustomException {

    public PasswordContainsNicknameException() {
        super(ErrorCode.PASSWORD_CONTAINS_NICKNAME_EXCEPTION);
    }
}
