package com.kacper.linkshortener.service;

public interface LinkValidator {

    /**
     * Checks if the passed link is a valid link
     * @return true or false, depending if link is valid.
     */
    boolean validateLink(String link);

    /**
     * Verifies that the given redirect link ID is unique.
     * @param link ID to test, will be returned if unique.
     * @return An unique ID.
     */
    String uniqueLinkIDValidator(String link);
}
