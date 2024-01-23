package com.ThePrince.PortfolioManagementSystem.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedActionException extends RuntimeException{
    public UnauthorizedActionException(String string){
        super(string);
    }
}
