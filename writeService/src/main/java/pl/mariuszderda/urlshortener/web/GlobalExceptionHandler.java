package pl.mariuszderda.urlshortener.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.mariuszderda.urlshortener.exception.UrlExpiredException;
import pl.mariuszderda.urlshortener.exception.UrlNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    ResponseEntity<String> handleNotFound(UrlNotFoundException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(UrlExpiredException.class)
    ResponseEntity<String> handleExpired(UrlExpiredException ex){
        return ResponseEntity.status(410).body(ex.getMessage());
    }
}
