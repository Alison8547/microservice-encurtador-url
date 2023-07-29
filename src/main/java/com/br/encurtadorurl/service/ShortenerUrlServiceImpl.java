package com.br.encurtadorurl.service;

import com.br.encurtadorurl.domain.ShortenerUrl;
import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import com.br.encurtadorurl.exception.BusinessException;
import com.br.encurtadorurl.mapper.ShortenerUrlMapper;
import com.br.encurtadorurl.repository.ShortenerUrlRepository;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortenerUrlServiceImpl implements ShortenerUrlService {

    private final ShortenerUrlRepository shortenerUrlRepository;
    private final ShortenerUrlMapper mapper;
    private static final Map<String, ShortenerUrl> map = new HashMap<>();
    private static final Integer DEFAULT_CLICKS = 0;


    @Override
    public ShortenerUrlResponse createShortUrl(ShortenerUrlRequest shortenerUrlRequest) {

        UrlValidator url = new UrlValidator(new String[]{"http", "https"});

        if (!url.isValid(shortenerUrlRequest.getOriginUrl())) {
            throw new BusinessException("Invalid url!");
        }

        ShortenerUrl shortenerUrlEntity = mapper.toShortenerUrlEntity(shortenerUrlRequest);

        String hashUrl = Hashing.murmur3_32_fixed().hashString(shortenerUrlRequest.getOriginUrl(), StandardCharsets.UTF_8).toString();
        String hostHashUrl = "http://localhost:8080/s/" + hashUrl;

        if (shortenerUrlRepository.existsByShortUrl(hostHashUrl)) {
            return mapper.toShortenerUrlResponse(shortenerUrlRepository.findByShortUrl(hostHashUrl));
        }

        shortenerUrlEntity.setShortUrl(hostHashUrl);
        shortenerUrlEntity.setHash(hashUrl);
        shortenerUrlEntity.setTimeRegister(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        shortenerUrlEntity.setTotalClicks(DEFAULT_CLICKS);
        shortenerUrlRepository.save(shortenerUrlEntity);
        log.info("Saved ShortenerUrl success!");

        return mapper.toShortenerUrlResponse(shortenerUrlEntity);
    }

    @Override
    public void getRedirectUrlOrigin(HttpServletResponse httpServletResponse, String hash) throws IOException {
        ShortenerUrl shortenerUrl = findHash(hash);
        map.put(hash, shortenerUrl);
        httpServletResponse.sendRedirect(map.get(hash).getOriginUrl());
        shortenerUrl.setLastAccess(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        shortenerUrl.setTotalClicks(shortenerUrl.getTotalClicks() + 1);
        shortenerUrlRepository.save(shortenerUrl);
    }

    @Override
    public ShortenerUrlMetricResponse getMetricUrl(String shortUrl) {
        return mapper.toShortenerUrlMetricResponse(findHash(shortUrl));
    }

    public ShortenerUrl findHash(String hash) {
        return shortenerUrlRepository.findByHash(hash)
                .orElseThrow(() -> new BusinessException("Url short not found!"));
    }

}
