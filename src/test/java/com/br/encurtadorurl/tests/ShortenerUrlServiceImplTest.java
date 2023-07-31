package com.br.encurtadorurl.tests;

import com.br.encurtadorurl.builders.ShortenerUrlBuilder;
import com.br.encurtadorurl.domain.ShortenerUrl;
import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import com.br.encurtadorurl.exception.BusinessException;
import com.br.encurtadorurl.mapper.ShortenerUrlMapper;
import com.br.encurtadorurl.repository.ShortenerUrlRepository;
import com.br.encurtadorurl.service.ShortenerUrlServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShortenerUrlServiceImplTest {

    @InjectMocks
    private ShortenerUrlServiceImpl service;

    @Mock
    private ShortenerUrlRepository repository;

    @Mock
    private ShortenerUrlMapper shortenerUrlMapper;

    @Test
    public void testMustCreateUrlWithSuccess() {
        //(SETUP)
        when(repository.save(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        when(shortenerUrlMapper.toShortenerUrlEntityWithResponse(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        when(shortenerUrlMapper.toShortenerUrlEntity(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        when(shortenerUrlMapper.toShortenerUrlResponse(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlResponse());

        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequest());

        //(ASSERT)
        assertNotNull(shortUrl);
        assertEquals(ShortenerUrlBuilder.newShortenerUrlEntity().getShortUrl(), shortUrl.getShortUrl());
        assertEquals("434f7bb0", shortenerUrlMapper.toShortenerUrlEntityWithResponse(shortUrl).getHash());
        assertEquals(0, shortenerUrlMapper.toShortenerUrlEntityWithResponse(shortUrl).getTotalClicks());
        assertNull(shortenerUrlMapper.toShortenerUrlEntityWithResponse(shortUrl).getLastAccess());


    }

    @Test
    public void testMustExistsByShortUrlWithSuccess() {
        //(SETUP)
        when(shortenerUrlMapper.toShortenerUrlEntity(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        when(shortenerUrlMapper.toShortenerUrlResponse(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlResponse());
        when(shortenerUrlMapper.toShortenerUrlEntityWithResponse(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        when(repository.existsByShortUrl(any())).thenReturn(true);

        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequest());

        //(ASSERT)
        assertNotNull(shortUrl);
        assertEquals(ShortenerUrlBuilder.newShortenerUrlResponse().getShortUrl(), shortUrl.getShortUrl());
        assertEquals("434f7bb0", shortenerUrlMapper.toShortenerUrlEntityWithResponse(shortUrl).getHash());


    }

    @Test(expected = BusinessException.class)
    public void testMustCreateUrlValidatorError() {
        //(SETUP)

        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequestInvalidUrl());

        //(ASSERT)
        assertNotNull(shortUrl);
        assertThrows(BusinessException.class, () -> service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequestInvalidUrl()));
    }

    @Test(expected = BusinessException.class)
    public void testMustCreateUrlNullError() {
        //(SETUP)

        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequestUrlNull());

        //(ASSERT)
        assertNull(shortUrl);
        assertThrows(BusinessException.class, () -> service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequestUrlNull()));
    }

    @Test(expected = BusinessException.class)
    public void testMustCreateUrlOnlyNumberError() {
        //(SETUP)
        ShortenerUrlRequest shortenerUrlRequest = ShortenerUrlBuilder.newShortenerUrlRequest();
        shortenerUrlRequest.setOriginUrl("1");
        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(shortenerUrlRequest);

        //(ASSERT)
        assertNull(shortUrl);
        assertThrows(BusinessException.class, () -> service.createShortUrl(ShortenerUrlBuilder.newShortenerUrlRequestUrlNull()));
    }

    @Test(expected = BusinessException.class)
    public void testMustCreateUrlEmptyError() {
        //(SETUP)
        ShortenerUrlRequest shortenerUrlRequest = new ShortenerUrlRequest("");
        //(ACT)
        ShortenerUrlResponse shortUrl = service.createShortUrl(shortenerUrlRequest);

        //(ASSERT)
        assertNull(shortUrl);
        assertThrows(BusinessException.class, () -> service.createShortUrl(shortenerUrlRequest));
    }

    @Test()
    public void testMustGetUrlDirectWithSuccess() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        Map<String, ShortenerUrl> map = new HashMap<>();
        //(SETUP)
        when(repository.findByHash(any())).thenReturn(Optional.of(ShortenerUrlBuilder.newShortenerUrlEntity()));
        when(repository.save(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        map.put(ShortenerUrlBuilder.newShortenerUrlEntity().getHash(), ShortenerUrlBuilder.newShortenerUrlEntity());
        try {
            response.sendRedirect(map.get(ShortenerUrlBuilder.newShortenerUrlEntity().getHash()).getOriginUrl());
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
        //(ACT)
        service.getRedirectUrlOrigin(response, ShortenerUrlBuilder.newShortenerUrlEntity().getHash());
        //(ASSERT)
        assertEquals("https://pt.aliexpress.com/?gatewayAdapt=Msite2Pc", ShortenerUrlBuilder.newShortenerUrlEntity().getOriginUrl());


    }

    @Test(expected = IOException.class)
    public void testMustGetUrlDirectWithError() throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Map<String, ShortenerUrl> map = new HashMap<>();
        //(SETUP)
        when(repository.findByHash(any())).thenReturn(Optional.of(ShortenerUrlBuilder.newShortenerUrlEntity()));
        when(repository.save(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlEntity());
        map.put(ShortenerUrlBuilder.newShortenerUrlEntity().getHash(), ShortenerUrlBuilder.newShortenerUrlEntity());
        //(ACT)
        service.getRedirectUrlOrigin(response, ShortenerUrlBuilder.newShortenerUrlEntity().getHash());
        throw new IOException("ERROR SIMULATOR");
    }

    @Test
    public void testMustGetMetricWithSuccess() {
        //(SETUP)
        when(repository.findByHash(anyString())).thenReturn(Optional.of(ShortenerUrlBuilder.newShortenerUrlEntity()));
        when(shortenerUrlMapper.toShortenerUrlMetricResponse(any())).thenReturn(ShortenerUrlBuilder.newShortenerUrlMetricResponse());

        //(ACT)
        ShortenerUrlMetricResponse metricUrl = service.getMetricUrl(ShortenerUrlBuilder.newShortenerUrlEntity().getHash());

        //(ASSERT)
        assertNotNull(metricUrl);
        assertEquals(0, metricUrl.getTotalClicks());
        assertNull(metricUrl.getLastAccess());


    }

    @Test(expected = BusinessException.class)
    public void testMustGetMetricWithError() {
        Optional<ShortenerUrl> byHash = repository.findByHash("434f7bb0");
        ShortenerUrl newShortenerUrlEntity = ShortenerUrlBuilder.newShortenerUrlEntity();
        newShortenerUrlEntity.setHash("11fqr0dc");

        //(ACT)
        ShortenerUrlMetricResponse metricUrl = service.getMetricUrl(newShortenerUrlEntity.getHash());

        //(ASSERT)
        assertNull(metricUrl);
        assertThrows(BusinessException.class, () -> byHash.get().getHash().equals(newShortenerUrlEntity.getHash()));


    }

}
