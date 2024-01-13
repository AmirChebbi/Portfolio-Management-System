package com.ThePrince.PortfolioManagementSystem.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnothorizedActionException extends RuntimeException{
    public UnothorizedActionException(String string){
        super(string);
    }
}
