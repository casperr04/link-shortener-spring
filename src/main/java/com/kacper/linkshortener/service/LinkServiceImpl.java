package com.kacper.linkshortener.service;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.model.entity.LinkEntity;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.utils.LinkUrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service
public class LinkServiceImpl implements LinkService {
    private final LinkConstants linkConstants;
    private final LinkUrlGenerator linkUrlGenerator;
    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceImpl(LinkUrlGenerator linkUrlGenerator, LinkRepository linkRepository, LinkConstants linkConstants) {
        this.linkUrlGenerator = linkUrlGenerator;
        this.linkRepository = linkRepository;
        this.linkConstants = linkConstants;
    }


    /**
     * @TODO Verify the link is an actual link
     * Handles validation and creation of a redirect link.
     * Expires after length defined in constants (default is 5 days).
     * @param link String of the link
     * @return LinkCreationResponse Model with the newly created URL link ID.
     * @throws RuntimeException if link is empty.
     */
    @Override
    public LinkCreationResponse createShortenedLink(String link) throws RuntimeException{

        if(link == null || link.isBlank()){
            throw new RuntimeException("Link not provided.");
        }

        LinkEntity linkEntity = new LinkEntity();

        String generatedUrl = recursiveUniqueLinkValidator(linkUrlGenerator.generateRandomID(6));

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
     * Retrieves the original link from a redirect ID.
     * @param link Redirect ID link.
     * @return Original link
     * @throws RuntimeException If link is null or blank
     */

    @Override
    public LinkRedirectResponse retrieveLink(String link) throws RuntimeException{
        if(link == null || link.isBlank()){throw new RuntimeException("Link not provided.");}

        LinkEntity linkEntity = linkRepository.findByRedirectLink(link);
        if (linkEntity == null){throw new RuntimeException("Link not found");}

        return new LinkRedirectResponse(linkEntity.getOriginalLink());
    }


    /**
     * Checks if given redirect link ID is unique.
     * Returns a different unique redirect link ID if a collision is found.
     * @param link Link ID to validate
     * @return An unique redirect link ID.
     */
    private String recursiveUniqueLinkValidator(String link)
    {
        if (linkRepository.findByRedirectLink(link) != null){
            return recursiveUniqueLinkValidator(linkUrlGenerator.generateRandomID(link.length()));
        }
        else{return link;}
    }
}
