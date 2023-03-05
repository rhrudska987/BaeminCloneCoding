package baemin.baeminjpa.config;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseException extends Exception{
    private BaseResponseStatus status;

}
