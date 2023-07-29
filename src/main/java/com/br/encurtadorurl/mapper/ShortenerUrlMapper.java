package com.br.encurtadorurl.mapper;

import com.br.encurtadorurl.domain.ShortenerUrl;
import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortenerUrlMapper {

    private final ModelMapper mapper;

    public ShortenerUrl toShortenerUrlEntity(ShortenerUrlRequest shortenerUrlRequest) {
        return mapper.map(shortenerUrlRequest, ShortenerUrl.class);
    }

    public ShortenerUrlResponse toShortenerUrlResponse(ShortenerUrl shortenerUrl) {
        return mapper.map(shortenerUrl, ShortenerUrlResponse.class);
    }
}
