package org.example.exception;

public class NotEnoughFundsException extends Exception{

    NotEnoughFundsException(){}

    public NotEnoughFundsException(String msg){
        super(msg);
    }
}
