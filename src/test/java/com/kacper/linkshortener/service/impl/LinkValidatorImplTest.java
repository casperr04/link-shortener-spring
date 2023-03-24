package com.kacper.linkshortener.service.impl;

import com.kacper.linkshortener.constants.LinkConstants;
import com.kacper.linkshortener.repository.LinkRepository;
import com.kacper.linkshortener.service.LinkUrlGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LinkValidatorImplTest {


    @InjectMocks
    @Autowired
    LinkValidatorImpl linkValidator;
    @Mock
    LinkRepository linkRepository;
    @Mock
    LinkUrlGenerator linkUrlGenerator;
    @Spy
    LinkConstants linkConstants = new LinkConstants();

    @Test
    void validateLink() {
        String validLink1 = "https://www.google.com";
        String validLink2 = "http://stackoverflow.com";
        String invalidLink1 = "www.facebook.com";
        String invalidLink2 = "invalidlink.com";
        String invalidLink3 = "invalid";

        assertTrue(linkValidator.validateLink(validLink1));
        assertTrue(linkValidator.validateLink(validLink2));

        assertFalse(linkValidator.validateLink(invalidLink1));
        assertFalse(linkValidator.validateLink(invalidLink2));
        assertFalse(linkValidator.validateLink(invalidLink3));
    }
    @Test
    void uniqueLinkIDValidator(){
        when(linkRepository.findByRedirectLink(anyString())).thenReturn(null);
        assertEquals(linkValidator.uniqueLinkIDValidator("ABC123"), "ABC123");
    }
}