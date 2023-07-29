package com.br.encurtadorurl.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenerUrlMetricResponse {

    private String originUrl;
    private LocalDateTime timeRegister;
    private LocalDateTime lastAccess;
    private Integer totalClicks;
}
