package com.vongthaya.backenddemo.exception;

import com.vongthaya.backenddemo.entity.User;

public class UserException extends BaseException {

    public UserException(String code) {
        super(code);
    }

    public static UserException emailNullOrBlank() {
        return new UserException("Email is required.");
    }

    public static UserException passwordNullOrBlank() {
        return new UserException("Password is required");
    }

    public static UserException nameNullOrBlank() {
        return new UserException("Name is required");
    }

    public static UserException emailDuplicate() {
        return new UserException("Email is duplicate.");
    }

    public static UserException requestNull() {
        return new UserException("Request is null.");
    }

    public static UserException loginFail() {
        return new UserException("Email or Password invalid");
    }

    public static UserException notFound() {
        return new UserException("User not found");
    }
}
