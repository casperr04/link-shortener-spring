package com.kacper.linkshortener.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinkUrlGeneratorImplTest {
    @InjectMocks
    @Autowired
    LinkUrlGeneratorImpl linkUrlGenerator;

    @Test
    void generateRandomID() {
        assertEquals(8, linkUrlGenerator.generateRandomID(8).length());
    }

    @Test
    void generateRandomString() {
        Pattern pattern = Pattern.compile("[ -~]");
        Matcher validIdTest = pattern.matcher(linkUrlGenerator.generateRandomString(8));
        assertEquals(8, linkUrlGenerator.generateRandomString(8).length());
        assertTrue(validIdTest.find());
    }
}