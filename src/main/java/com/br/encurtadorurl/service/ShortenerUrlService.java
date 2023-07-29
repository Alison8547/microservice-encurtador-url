package com.br.encurtadorurl.service;

import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;

import javax.transaction.Transactional;

public interface ShortenerUrlService {

    @Transactional
    ShortenerUrlResponse createShortUrl(ShortenerUrlRequest shortenerUrlRequest);
}
