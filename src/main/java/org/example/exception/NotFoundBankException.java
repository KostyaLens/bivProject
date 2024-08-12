package org.example.exception;

public class NotFoundBankException extends Exception{

    NotFoundBankException(){}

    public NotFoundBankException(String msg){
        super(msg);
    }
}
