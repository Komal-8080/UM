package com.usermgmt.exceptions;

public class UserException extends RuntimeException {

    public ExceptionTypes exceptionTypes;

    public UserException(ExceptionTypes exceptionTypes){
        this.exceptionTypes = exceptionTypes;
    }

    public enum ExceptionTypes {
        USER_EXISTS("User Already Registered"),
        INVALID_EMAIL("InValid EmailId Entered"),
        EMAIL_NOT_FOUND("User Email Id Not Found"),
        INVALID_USERNAME_OR_PASSWORD("UserName or Password is Invalid"),
        USER_NOT_FOUND("User Not Found"),
        INVAlID_TOKEN("User Token Is Invalid"),
        PASSWORD_DOES_NOT_MATCH("New password and Confirm password does not match"),
        UNABLE_TO_READ_FILE("Unable to Read File");

        public String errorMessage;

        ExceptionTypes(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
}
