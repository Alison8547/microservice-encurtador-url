package com.br.encurtadorurl.controller;

import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Validated
@RequestMapping
public interface ShortenerUrlController {


    @PostMapping("/create-short-url")
    ResponseEntity<ShortenerUrlResponse> create(@Valid @RequestBody ShortenerUrlRequest shortenerUrlRequest);

    @GetMapping("/s/{hash}")
    ResponseEntity<Void> redirectUrlOrigin(HttpServletResponse response, @PathVariable(name = "hash") String hash);
}
