package com.sbnz.adsys.exception;

import com.sbnz.adsys.model.ErrorMessages;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BadRequestException extends RuntimeException {
    private ErrorMessages errorMessages;
    
    public BadRequestException(String msg) {
        this.errorMessages = new ErrorMessages(HttpStatus.BAD_REQUEST.value(), "Not authorize", msg);
    }
}
