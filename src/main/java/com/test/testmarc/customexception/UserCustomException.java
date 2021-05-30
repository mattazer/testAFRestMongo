package com.test.testmarc.customexception;

/**
 * Exception correspondant à une mauvaise saisie utilisateur
 */
public class UserCustomException extends Exception{

    private String message;

    public UserCustomException(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
}
