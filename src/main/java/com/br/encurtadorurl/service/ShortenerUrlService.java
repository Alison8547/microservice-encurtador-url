package com.br.encurtadorurl.service;

import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

public interface ShortenerUrlService {

    @Transactional
    ShortenerUrlResponse createShortUrl(ShortenerUrlRequest shortenerUrlRequest);

    void getRedirectUrlOrigin(HttpServletResponse httpServletResponse, String hash);

    ShortenerUrlMetricResponse getMetricUrl(String shortUrlHash);
}
