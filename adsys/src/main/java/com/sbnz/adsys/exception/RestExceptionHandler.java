package com.sbnz.adsys.exception;

import com.sbnz.adsys.model.ErrorMessages;
import org.h2.security.auth.AuthConfigException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessages> processAuthException(AuthException ex) {
        return new ResponseEntity<>(ex.getErrorMessages(),
                HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessages> processBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(ex.getErrorMessages(),
                HttpStatus.UNAUTHORIZED);
    }
    
}
