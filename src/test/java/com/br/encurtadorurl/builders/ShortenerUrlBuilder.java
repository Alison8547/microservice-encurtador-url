package com.br.encurtadorurl.builders;

import com.br.encurtadorurl.domain.ShortenerUrl;
import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ShortenerUrlBuilder {

    public static ShortenerUrl newShortenerUrlEntity() {
        return ShortenerUrl.builder()
                .urlId(1)
                .originUrl("https://pt.aliexpress.com/?gatewayAdapt=Msite2Pc")
                .shortUrl("http://localhost:8080/s/434f7bb0")
                .hash("434f7bb0")
                .timeRegister(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .totalClicks(0)
                .build();
    }

    public static ShortenerUrlResponse newShortenerUrlResponse() {
        return ShortenerUrlResponse.builder()
                .shortUrl("http://localhost:8080/s/434f7bb0")
                .build();
    }

    public static ShortenerUrlRequest newShortenerUrlRequest() {
        return ShortenerUrlRequest.builder()
                .originUrl("https://pt.aliexpress.com/?gatewayAdapt=Msite2Pc")
                .build();
    }

    public static ShortenerUrlRequest newShortenerUrlRequestInvalidUrl() {
        return ShortenerUrlRequest.builder()
                .originUrl("hs://pt.aliexpress.com/?gatewayAdapt=Msite2Pc")
                .build();
    }

    public static ShortenerUrlRequest newShortenerUrlRequestUrlNull() {
        return ShortenerUrlRequest.builder()
                .originUrl(null)
                .build();
    }

    public static ShortenerUrlMetricResponse newShortenerUrlMetricResponse() {
        return ShortenerUrlMetricResponse.builder()
                .originUrl("https://pt.aliexpress.com/?gatewayAdapt=Msite2Pc")
                .timeRegister(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .lastAccess(null)
                .totalClicks(0)
                .build();
    }
}
