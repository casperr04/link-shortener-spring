package com.kacper.linkshortener.utils;
import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Random;


@Component
public class LinkUrlGenerator {
    private Random random = new SecureRandom();
    private String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public Random getRandom() {
        return random;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String generateRandomID(int length) {
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder();
        for (int i = 0; i < length; i++) {
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return new String(returnValue);
    }
}
