package com.kacper.linkshortener.controller;


import com.kacper.linkshortener.model.request.LinkRequestModel;
import com.kacper.linkshortener.model.response.ExceptionResponseModel;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Create redirect link", description = "Creates a redirect link for the original link passed in the body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successfully created, returns redirect URL",
                    content = @Content(schema = @Schema(implementation = LinkCreationResponse.class))),
            @ApiResponse(responseCode = "400",
                description = "Invalid request",
                content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})

    @PostMapping(path = "/link",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
    public ResponseEntity<?> createShortenedLink(@RequestBody LinkRequestModel linkRequestModel, HttpServletRequest request) {
        LinkCreationResponse linkCreationResponse;
        try {
            linkCreationResponse = linkService.createShortenedLink(linkRequestModel.getLink(), request);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ExceptionResponseModel(runtimeException.getMessage(), LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(linkCreationResponse);
    }

    /**
     * Endpoint for retrieving the original link
     * @param id Redirect ID
     * @return Redirect to specified original URL
     */
    @Operation(summary = "Redirect to original URL",
            description = "Redirects to the original link from a created redirect link. Uses ID from the returned url (/l{id}nk)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302",
                    description = "Successfully found, redirects to original URL",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponseModel.class)))})

    @GetMapping(path = "/li{id}k",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_ATOM_XML_VALUE})
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
