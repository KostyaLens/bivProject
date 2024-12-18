package org.example.exception;

public class WrongPinCodeException extends Exception{

    WrongPinCodeException(){}

    public WrongPinCodeException(String msg){
        super(msg);
    }
}
