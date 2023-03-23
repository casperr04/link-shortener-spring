package com.kacper.linkshortener.service;

public interface LinkValidator {

    /**
     * Checks if the passed link is a valid link.
     */
    boolean validateLink(String link);

    /**
     * Verifies that the given redirect link ID is unique.
     */
    String uniqueLinkIDValidator(String link);
}
