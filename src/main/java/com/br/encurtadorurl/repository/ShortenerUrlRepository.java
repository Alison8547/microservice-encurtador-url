package com.br.encurtadorurl.repository;

import com.br.encurtadorurl.domain.ShortenerUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortenerUrlRepository extends JpaRepository<ShortenerUrl, Integer> {
}
