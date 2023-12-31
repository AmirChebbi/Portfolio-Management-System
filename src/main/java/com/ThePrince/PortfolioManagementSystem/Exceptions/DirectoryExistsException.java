package com.ThePrince.PortfolioManagementSystem.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DirectoryExistsException extends RuntimeException{
    public DirectoryExistsException(String message){
        super (message);
    }
}
