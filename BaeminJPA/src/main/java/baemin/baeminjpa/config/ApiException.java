package baemin.baeminjpa.config;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException {
    private final boolean isSucess;
    private final String message;
    private final HttpStatus httpStatus;

    @Builder
    public ApiException(String message, HttpStatus httpStatus){
        this.isSucess = false;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
