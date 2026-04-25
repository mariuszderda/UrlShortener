package pl.mariuszderda.urlshortener.web;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mariuszderda.urlshortener.dto.ShortenRequest;
import pl.mariuszderda.urlshortener.dto.ShortenResponse;
import pl.mariuszderda.urlshortener.service.UrlShortenerService;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("shorten")
    ResponseEntity<ShortenResponse> createShorten (@RequestBody ShortenRequest request){
        var shortenUrl = urlShortenerService.shorten(request.url());
        var responseUrl = shortenUrl.shortCode();
        var response = new ShortenResponse("http://localhost:8080/" + responseUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
