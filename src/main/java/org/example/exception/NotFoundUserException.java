package org.example.exception;

public class NotFoundUserException extends Exception{

    NotFoundUserException(){}

    public NotFoundUserException(String msg){
        super(msg);
    }
}
