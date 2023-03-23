package com.kacper.linkshortener.service.impl;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.model.entity.LinkEntity;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.service.LinkService;
import com.kacper.linkshortener.service.LinkValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkConstants linkConstants;
    private final LinkUrlGeneratorImpl linkUrlGeneratorImpl;
    private final LinkRepository linkRepository;
    private final LinkValidator linkValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkCreationResponse createShortenedLink(String link) throws RuntimeException{
        if(link == null || link.isBlank()){
            throw new RuntimeException("Link not provided.");
        }
        if (!link.startsWith("https://") || !link.startsWith("http://")){
            link = "https://" + link;
        }
        if (!linkValidator.validateLink(link)) {
            throw new RuntimeException("Link is not valid.");
        }

        LinkEntity linkEntity = new LinkEntity();

        String generatedUrl = linkValidator.uniqueLinkIDValidator(linkUrlGeneratorImpl.generateRandomID(6));

        // Adds prefix and suffix to prevent Controller calling method for empty path.
        String preparedLink = linkConstants.getCONTROLLER_PREFIX()
                + generatedUrl
                + linkConstants.getCONTROLLER_SUFFIX();

        linkEntity.setOriginalLink(link);
        linkEntity.setRedirectLink(generatedUrl);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusDays(linkConstants.getENTITY_EXPIRATION_DAYS());
        Timestamp timestamp = Timestamp.valueOf(expirationTime);
        linkEntity.setExpirationDate(timestamp.getTime());

        linkRepository.save(linkEntity);

        //Prepared link includes prefix, store generatedUrl by itself.
        return new LinkCreationResponse(preparedLink, expirationTime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkRedirectResponse retrieveLink(String link) throws RuntimeException{
        if(link == null || link.isBlank()){throw new RuntimeException("Link not provided.");}

        LinkEntity linkEntity = linkRepository.findByRedirectLink(link);
        if (linkEntity == null){throw new RuntimeException("Link not found");}

        return new LinkRedirectResponse(linkEntity.getOriginalLink());
    }
}
