package com.kacper.linkshortener.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LinkRequestModel {
    @Schema(name = "link", type = "url", format = "string", description = "Link to be shortened", example = "www.youtube.com")
    private String link;
}
