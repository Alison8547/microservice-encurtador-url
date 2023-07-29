package com.br.encurtadorurl.service;

import com.br.encurtadorurl.domain.ShortenerUrl;
import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import com.br.encurtadorurl.exception.BusinessException;
import com.br.encurtadorurl.mapper.ShortenerUrlMapper;
import com.br.encurtadorurl.repository.ShortenerUrlRepository;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShortenerUrlServiceImpl implements ShortenerUrlService {

    private final ShortenerUrlRepository shortenerUrlRepository;
    private final ShortenerUrlMapper mapper;


    @Override
    public ShortenerUrlResponse createShortUrl(ShortenerUrlRequest shortenerUrlRequest) {

        UrlValidator url = new UrlValidator(new String[]{"http", "https"});

        if (!url.isValid(shortenerUrlRequest.getOriginUrl())) {
            throw new BusinessException("Invalid url!");
        }

        ShortenerUrl shortenerUrlEntity = mapper.toShortenerUrlEntity(shortenerUrlRequest);

        String hashUrl = Hashing.murmur3_32_fixed().hashString(shortenerUrlRequest.getOriginUrl(), StandardCharsets.UTF_8).toString();

        if (shortenerUrlRepository.existsByShortUrl(hashUrl)) {
            return mapper.toShortenerUrlResponse(shortenerUrlRepository.findByShortUrl(hashUrl));
        }

        shortenerUrlEntity.setShortUrl(hashUrl);
        shortenerUrlEntity.setTimeRegister(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        shortenerUrlRepository.save(shortenerUrlEntity);
        log.info("Saved ShortenerUrl success!");

        return mapper.toShortenerUrlResponse(shortenerUrlEntity);
    }

}
