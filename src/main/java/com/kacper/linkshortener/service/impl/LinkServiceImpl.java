package com.kacper.linkshortener.service.impl;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.model.entity.LinkEntity;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.service.LinkService;
import com.kacper.linkshortener.service.LinkUrlGenerator;
import com.kacper.linkshortener.service.LinkValidator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.time.LocalDateTime;


@Service("linkService")
@AllArgsConstructor
public class LinkServiceImpl implements LinkService {
    private final LinkConstants linkConstants;
    private final LinkUrlGenerator linkUrlGenerator;
    private final LinkRepository linkRepository;
    private final LinkValidator linkValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public LinkCreationResponse createShortenedLink(String link, HttpServletRequest request) throws RuntimeException{
        if(link == null || link.isBlank()){
            throw new RuntimeException("Link not provided.");
        }
        if (!link.startsWith("https://") && !link.startsWith("http://")){
            link = "https://" + link;
        }
        if (!linkValidator.validateLink(link)) {
            throw new RuntimeException("Link is not valid.");
        }

        LinkEntity linkEntity = new LinkEntity();

        String generatedUrl = linkValidator.uniqueLinkIDValidator(linkUrlGenerator.generateRandomID(6));

        // Builds the redirect link that is returned to the user
        String preparedLink = request.getHeader("Host")
                + "/"
                + linkConstants.getCONTROLLER_PREFIX()
                + generatedUrl
                + linkConstants.getCONTROLLER_SUFFIX();

        linkEntity.setOriginalLink(link);
        linkEntity.setRedirectLink(generatedUrl);

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime
                .plusDays(linkConstants.getENTITY_EXPIRATION_DAYS())
                .plusHours(linkConstants.getENTITY_EXPIRATION_HOURS())
                .plusMinutes(linkConstants.getENTITY_EXPIRATION_MINUTES());

        Timestamp timestamp = Timestamp.valueOf(expirationTime);
        linkEntity.setExpirationDate(timestamp.getTime());

        linkRepository.save(linkEntity);

        //Prepared link includes prefix and suffix, store generatedUrl by itself.
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

    /**
     * Removes expired links from the database.
     */
    @Scheduled(cron = "${cron_expiration_date}")
    private void removeExpiredLinks() {
        Logger logger = LoggerFactory.getLogger(LinkService.class);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        int removalCount = linkRepository.countAllByExpirationDateIsLessThan(timestamp.getTime());
        linkRepository.removeAllByExpirationDateIsLessThan(timestamp.getTime());
        logger.info("--SCHEDULED EXPIRED LINK REMOVAL--");
        logger.info("Removed " + removalCount + " expired links from database.");
    }
}
