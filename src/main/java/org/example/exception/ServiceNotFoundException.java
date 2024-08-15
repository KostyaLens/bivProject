package org.example.exception;

public class ServiceNotFoundException extends Exception{
    ServiceNotFoundException(){}

    public ServiceNotFoundException(String msg){
        super(msg);
    }
}
