package pl.mariuszderda.urlshortener.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mariuszderda.urlshortener.service.UrlShortenerService;

@Controller
public class UrlRedirectController {

    private final UrlShortenerService urlShortenerService;

    public UrlRedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }


    @GetMapping("{shortCode}")
    ResponseEntity<Void> getOriginalUrl(@PathVariable String shortCode){
        var originalUrl = urlShortenerService.resolve(shortCode);
        return ResponseEntity.status(302).header("Location", originalUrl).build();
    }
}
