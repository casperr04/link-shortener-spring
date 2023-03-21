package com.kacper.linkshortener.service;

public interface LinkUrlGenerator {
    String generateRandomID(int length);

    String generateRandomString(int length);
}
