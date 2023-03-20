package com.kacper.linkshortener.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkCreationResponse {
    private String link;
    private LocalDateTime expirationDate;
}
