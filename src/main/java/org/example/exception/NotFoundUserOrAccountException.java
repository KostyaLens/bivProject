package org.example.exception;

public class NotFoundUserOrAccountException extends Exception{

    NotFoundUserOrAccountException(){}

    public NotFoundUserOrAccountException(String msg){
        super(msg);
    }
}
