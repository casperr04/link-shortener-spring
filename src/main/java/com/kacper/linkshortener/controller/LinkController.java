package com.kacper.linkshortener.controller;


import com.kacper.linkshortener.model.request.LinkRequestModel;
import com.kacper.linkshortener.model.response.ExceptionResponseModel;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@RestController
public class LinkController {
    private final LinkService linkService;

    /**
     * Endpoint for creating redirect URL links.
     * @return LinkCreationResponse consisting of a new URL redirect ID and expiration date.
     */
    @PostMapping(path = "/link")
    public ResponseEntity<?> createShortenedLink(@RequestBody LinkRequestModel linkRequestModel) {
        LinkCreationResponse linkCreationResponse;
        try {
            linkCreationResponse = linkService.createShortenedLink(linkRequestModel.getLink());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponseModel(e.getMessage(), LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(linkCreationResponse);
    }

    /**
     * Endpoint for retrieving the original link
     * @param id Redirect ID
     * @return Redirect to specified original URL
     */
    @GetMapping(path = "/li{id}k")
    public ResponseEntity<?> retrieveRedirectLinkId(@PathVariable String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        try {
            httpHeaders.add("Location", linkService.retrieveLink(id).getRedirectLink());
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), LocalDateTime.now()));
        }
        return new ResponseEntity<String>(httpHeaders, HttpStatus.FOUND);
    }
}
