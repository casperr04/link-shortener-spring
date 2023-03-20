package com.kacper.linkshortener.service;

import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;

public interface LinkService {
    LinkCreationResponse createShortenedLink(String link);
    LinkRedirectResponse retrieveLink(String link);
}
