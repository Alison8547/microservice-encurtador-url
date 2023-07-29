package com.br.encurtadorurl.repository;

import com.br.encurtadorurl.domain.ShortenerUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenerUrlRepository extends JpaRepository<ShortenerUrl, Integer> {

    boolean existsByShortUrl(String shortUrl);

    ShortenerUrl findByShortUrl(String shortUrl);

    Optional<ShortenerUrl> findByHash(String hash);
}
