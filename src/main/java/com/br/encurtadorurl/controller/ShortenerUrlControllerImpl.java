package com.br.encurtadorurl.controller;

import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import com.br.encurtadorurl.service.ShortenerUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ShortenerUrlControllerImpl implements ShortenerUrlController {

    private final ShortenerUrlService urlService;

    @Override
    public ResponseEntity<ShortenerUrlResponse> create(ShortenerUrlRequest shortenerUrlRequest) {
        return new ResponseEntity<>(urlService.createShortUrl(shortenerUrlRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> redirectUrlOrigin(HttpServletResponse response, String hash) throws IOException {
        urlService.getRedirectUrlOrigin(response, hash);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ShortenerUrlMetricResponse> getMetricUrl(String shortUrlHash) {
        return new ResponseEntity<>(urlService.getMetricUrl(shortUrlHash), HttpStatus.OK);
    }
}
