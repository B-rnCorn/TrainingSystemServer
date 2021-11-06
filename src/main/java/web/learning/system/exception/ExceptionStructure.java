package web.learning.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ExceptionStructure {

    private int status;
    private String error;
    private String message;
    private Date date;
    private String URL;

    public static ExceptionStructure createException(GlobalException globalException, String path) {
        return new ExceptionStructure(globalException.getHttpStatus().value(), globalException.getHttpStatus().name(),
                globalException.getMessage(), new Date(System.currentTimeMillis()), path);
    }
}
