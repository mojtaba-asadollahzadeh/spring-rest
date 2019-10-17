package com.app.restApplication.utils;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.HashMap;
import java.util.Map;

/*
 * Class to generate a nice Error responses
 */

public class ApiError {

    private Errors errors;

    public ApiError(Errors errors) {
        this.setErrors(errors);
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Map<String, String> getAllErrors(){
        Map<String, String> errors = new HashMap<String, String>();
        for (FieldError error : this.errors.getFieldErrors()){
            errors.put(error.getField(),error.getDefaultMessage());
        }

        return errors;
    }
}
