package com.app.restApplication.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> send(
            HttpStatus status,
            boolean success,
            String message,
            Object responseObj
    ) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("success", success);
            map.put("message", message);
            if(success){
                map.put("data", responseObj);
            }else{
                map.put("errors", responseObj);
            }

            return new ResponseEntity<Object>(map,status);
        } catch (Exception e) {
            map.clear();
            map.put("timestamp", new Date());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            map.put("isSuccess",false);
            map.put("message", e.getMessage());
            map.put("data", null);
            return new ResponseEntity<Object>(map,status);
        }
    }
}
