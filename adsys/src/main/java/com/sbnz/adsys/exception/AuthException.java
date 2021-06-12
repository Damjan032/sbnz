package com.sbnz.adsys.exception;
import com.sbnz.adsys.model.ErrorMessages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthException extends RuntimeException {
    private ErrorMessages errorMessages;
    
    public AuthException(String msg) {
        this.errorMessages = new ErrorMessages(HttpStatus.UNAUTHORIZED.value(), "Not authorize", msg);
    }
    
}
