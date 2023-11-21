package com.example.minor_project1.exceptions;

public class BookLimitExceededException extends Exception{

    public BookLimitExceededException(String msg){
        super(msg);
    }

}
