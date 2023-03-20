package com.kacper.linkshortener.controller;


import com.kacper.linkshortener.model.request.LinkRequestModel;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.service.LinkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@AllArgsConstructor
@RestController
public class LinkController {
    private final LinkService linkService;


    /**
     * Endpoint for creating redirect URL links.
     * @return LinkCreationResponse consisting of a new URL redirect ID and expiration date.
     */
    @PostMapping(path = "/link")
    public LinkCreationResponse createShortenedLink(@RequestBody LinkRequestModel linkRequestModel){
        return linkService.createShortenedLink(linkRequestModel.getLink());
    }

    @GetMapping(path = "/li{id}k")
    public RedirectView retrieveRedirectLinkId(@PathVariable String id) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(linkService.retrieveLink(id).getRedirectLink());
        return redirectView;
    }
}
