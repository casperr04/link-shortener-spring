package com.kacper.linkshortener.service.impl;
import com.kacper.linkshortener.service.LinkUrlGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;


@Service
public class LinkUrlGeneratorImpl implements LinkUrlGenerator {
    private final Random random = new SecureRandom();

    @Override
    public String generateRandomID(int length) {
        return generateRandomString(length);
    }

    @Override
    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return new String(returnValue);
    }
}
