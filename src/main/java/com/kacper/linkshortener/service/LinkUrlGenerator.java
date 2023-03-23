package com.kacper.linkshortener.service;

public interface LinkUrlGenerator {

    /**
     * Generates a random ID given length.
     * @param length Length of the ID to be generated.
     * @return Generated ID string.
     */
    String generateRandomID(int length);

    /**
     * Generates a random String given length.
     * @param length Length of the String to be generated.
     * @return Generated string.
     */
    String generateRandomString(int length);
}
