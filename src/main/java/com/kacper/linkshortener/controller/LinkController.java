package com.kacper.linkshortener.controller;


import com.kacper.linkshortener.model.request.LinkRequestModel;
import com.kacper.linkshortener.model.response.LinkResponse;
import com.kacper.linkshortener.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class LinkController {
    private final LinkService linkService;

    @PostMapping(path = "/link")
    public LinkResponse createShortenedLink(@RequestBody LinkRequestModel linkRequestModel){
        return linkService.createShortenedLink(linkRequestModel.getLink());
    }


}
