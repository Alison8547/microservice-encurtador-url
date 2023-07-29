package com.br.encurtadorurl.controller;

import com.br.encurtadorurl.dto.request.ShortenerUrlRequest;
import com.br.encurtadorurl.dto.response.ShortenerUrlMetricResponse;
import com.br.encurtadorurl.dto.response.ShortenerUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Validated
@RequestMapping
public interface ShortenerUrlController {

    @Operation(summary = "Criar uma Url encurtada", description = "Salvar uma Url encurtada no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Criou com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/create-short-url")
    ResponseEntity<ShortenerUrlResponse> create(@Valid @RequestBody ShortenerUrlRequest shortenerUrlRequest);

    @Operation(hidden = true)
    @GetMapping("/s/{hash}")
    ResponseEntity<Void> redirectUrlOrigin(HttpServletResponse response, @PathVariable(name = "hash") String hash) throws IOException;

    @Operation(summary = "Pegar estatísticas de acesso às URLs geradas através do seu hash", description = "Retorna a estatística do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "400", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/get-metric/{shortUrlHash}")
    ResponseEntity<ShortenerUrlMetricResponse> getMetricUrl(@PathVariable(name = "shortUrlHash") String shortUrl);
}
