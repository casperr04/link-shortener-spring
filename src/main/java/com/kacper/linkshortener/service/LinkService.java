package com.kacper.linkshortener.service;

import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface LinkService {
    /**
     * Handles validation and creation of a redirect link.
     * Expires after length defined in constants (default is 5 days).
     * @param link String of the link
     * @return LinkCreationResponse Model with the created link.
     * @throws RuntimeException if link is empty.
     */
    LinkCreationResponse createShortenedLink(String link, HttpServletRequest request) throws RuntimeException;

    /**
     * Retrieves the original link from a redirect ID.
     * @param link Redirect ID link.
     * @return Original link
     * @throws RuntimeException If link is null or blank
     */
    LinkRedirectResponse retrieveLink(String link) throws RuntimeException;
}
