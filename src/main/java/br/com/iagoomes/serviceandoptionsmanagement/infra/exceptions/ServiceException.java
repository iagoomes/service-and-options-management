package br.com.iagoomes.serviceandoptionsmanagement.infra.exceptions;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
