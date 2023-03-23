package com.kacper.linkshortener.service.impl;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.service.LinkUrlGenerator;
import com.kacper.linkshortener.service.LinkValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class LinkValidatorImpl implements LinkValidator {

    private final LinkRepository linkRepository;
    private final LinkUrlGenerator linkUrlGenerator;
    private final LinkConstants linkConstants;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateLink(String link){
        Pattern pattern = Pattern.compile(linkConstants.getLINK_VALIDATION_REGEX());
        Matcher matcher = pattern.matcher(link);
        return matcher.find();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String uniqueLinkIDValidator(String link)
    {
        if (linkRepository.findByRedirectLink(link) != null){
            return uniqueLinkIDValidator(linkUrlGenerator.generateRandomID(link.length()));
        }
        else{return link;}
    }
}
