package com.kacper.linkshortener.service;

import com.kacper.linkshortener.model.response.LinkResponse;

public interface LinkService {
    LinkResponse createShortenedLink(String linkRequestModel);
}
