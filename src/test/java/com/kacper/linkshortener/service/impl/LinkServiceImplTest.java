package com.kacper.linkshortener.service.impl;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.model.entity.LinkEntity;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.service.LinkUrlGenerator;
import com.kacper.linkshortener.service.LinkValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {
    @Spy
    LinkConstants linkConstants = new LinkConstants();

    @Autowired
    @Mock
    LinkValidator linkValidator;

    @Mock
    LinkUrlGenerator linkUrlGenerator;
    @Mock
    LinkRepository linkRepository;

    @Autowired
    @InjectMocks
    LinkServiceImpl linkService;


    @Test
    void createShortenedLink() {
        when(linkValidator.validateLink(anyString())).thenReturn(false);
        assertThrows(RuntimeException.class, () -> linkService.createShortenedLink("string"));

        when(linkValidator.validateLink(anyString())).thenReturn(true);
        when(linkValidator.uniqueLinkIDValidator(any())).thenReturn("ABC123");
        LinkCreationResponse linkCreationResponse = linkService.createShortenedLink("myLink");
        assertEquals(linkCreationResponse.getLink(), linkConstants.getCONTROLLER_PREFIX() + "ABC123" + linkConstants.getCONTROLLER_SUFFIX());
    }

    @Test
    void retrieveLink() {
        assertThrows(RuntimeException.class, () -> linkService.retrieveLink(null));
        assertThrows(RuntimeException.class, () -> linkService.retrieveLink(""));

        when(linkRepository.findByRedirectLink(anyString())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> linkService.retrieveLink("MyLink"));

        LinkEntity linkEntity = new LinkEntity();
        linkEntity.setRedirectLink("liABC123k");
        linkEntity.setOriginalLink("https://www.youtube.com");
        when(linkRepository.findByRedirectLink(anyString())).thenReturn(linkEntity);
        LinkRedirectResponse linkRedirectResponse = linkService.retrieveLink("liABC123k");
        assertEquals("https://www.youtube.com", linkRedirectResponse.getRedirectLink());
    }
}