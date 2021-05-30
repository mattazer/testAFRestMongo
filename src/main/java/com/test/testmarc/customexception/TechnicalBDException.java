package com.test.testmarc.customexception;

/**
 * Exception Technique en cas de non enregistrement dans la base
 */
public class TechnicalBDException extends Exception{

    private String message;

    public TechnicalBDException(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
}
