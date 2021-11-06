package web.learning.system.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GlobalException extends RuntimeException {

    private HttpStatus httpStatus;

    public GlobalException(){
    }


    public GlobalException(String msg, HttpStatus httpStatus){
        super(msg);
        this.httpStatus = httpStatus;
    }

    public GlobalException(String msg, Throwable cause){
        super(msg, cause);
    }

    public GlobalException(Throwable cause){
        super(cause);
    }
}
