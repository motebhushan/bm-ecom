package com.ecommerse.project.exceptions;

import com.ecommerse.project.payload.APiResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

@RestControllerAdvice

public class MyGlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException e){
      Map<String,String> responce=new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fieldName=((FieldError)err).getField();
            String message=err.getDefaultMessage();
            responce.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(responce,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<APiResponce>myResourceNotFoundException(ResourceNotFound e){
        APiResponce aPiResponce=new APiResponce(e.getMessage(),false);
        return new ResponseEntity<>(aPiResponce,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APiResponce> myAPIException(APIException e) {
        APiResponce aPiResponce=new APiResponce(e.getMessage(),false);
        return new ResponseEntity<>(aPiResponce,HttpStatus.BAD_REQUEST);
    }

}
