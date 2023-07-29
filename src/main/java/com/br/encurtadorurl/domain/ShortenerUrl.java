package com.br.encurtadorurl.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "shortener_url")
public class ShortenerUrl implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer urlId;

    @Column(name = "origin_url")
    private String originUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "hash_url")
    private String hash;

    @Column(name = "time_register")
    private LocalDateTime timeRegister;

    @Column(name = "last_access")
    private LocalDateTime lastAccess;

    @Column(name = "total_clicks")
    private Integer totalClicks;

}
