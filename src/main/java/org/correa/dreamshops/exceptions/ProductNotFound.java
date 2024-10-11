package org.correa.dreamshops.exceptions;

public class ProductNotFound extends RuntimeException{
    public ProductNotFound(String message){
        super(message);
    }
}
