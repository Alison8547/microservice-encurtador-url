package com.br.encurtadorurl.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenerUrlRequest {

    @Schema(description = "Seu link", example = "https://www.google.com.br/?hl=pt-BR")
    @NotNull
    private String originUrl;
}
