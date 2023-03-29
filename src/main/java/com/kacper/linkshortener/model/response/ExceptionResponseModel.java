package com.kacper.linkshortener.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseModel {
    @Schema(name = "error message", type = "message", format = "string", description = "Error message", example = "Link not found")

    private String message;
    private LocalDateTime timestamp;
}
