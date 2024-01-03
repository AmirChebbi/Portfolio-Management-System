package com.ThePrince.PortfolioManagementSystem.Handler;

import com.ThePrince.PortfolioManagementSystem.DTOs.Directory.DirectoryPathDTO;
import com.nimbusds.oauth2.sdk.util.date.SimpleDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object object, HttpStatus status, List<DirectoryPathDTO> pathDTOS){
        HashMap<String, Object> map = new HashMap<>();
        map.put("http", status.value());
        map.put("status", "success");
        map.put("data",object);
        map.put("sendingDate", LocalDateTime.now());
        map.put("path", pathDTOS);
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(Object object, HttpStatus status){
        HashMap<String, Object> map = new HashMap<>();
        map.put("http", status.value());
        map.put("status", "success");
        map.put("data",object);
        map.put("sendingDate", LocalDateTime.now());
        return new ResponseEntity<>(map, status);
    }

}
