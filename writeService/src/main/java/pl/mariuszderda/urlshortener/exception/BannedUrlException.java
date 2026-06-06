package pl.mariuszderda.urlshortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BannedUrlException extends RuntimeException {
    public BannedUrlException(String message) {
        super(message);
    }
}
