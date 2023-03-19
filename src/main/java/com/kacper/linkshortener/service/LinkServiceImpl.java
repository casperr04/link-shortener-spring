package com.kacper.linkshortener.service;

import com.kacper.linkshortener.model.entity.LinkEntity;
import com.kacper.linkshortener.model.response.LinkResponse;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.utils.LinkUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class LinkServiceImpl implements LinkService {

    private final LinkUrlGenerator linkUrlGenerator;

    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceImpl(LinkUrlGenerator linkUrlGenerator, LinkRepository linkRepository) {
        this.linkUrlGenerator = linkUrlGenerator;
        this.linkRepository = linkRepository;
    }

    @Override
    public LinkResponse createShortenedLink(String link) throws RuntimeException{

        if(link == null || link.isBlank()){
            throw new RuntimeException("Link not provided.");
        }

        LinkEntity linkEntity = new LinkEntity();

        String generatedUrl = "";
        while(linkRepository.findByRedirectLink(generatedUrl) != null){
            generatedUrl = linkUrlGenerator.generateRandomID(6);
        }

        linkEntity.setOriginalLink(link);
        linkEntity.setRedirectLink(generatedUrl);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusDays(5);
        linkEntity.setExpirationDate(expirationTime);

        linkRepository.save(linkEntity);
        return new LinkResponse(generatedUrl, expirationTime);
    }
}
