package com.kacper.linkshortener.constants;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LinkConstants {
    private final String CONTROLLER_PREFIX = "li";
    private final String CONTROLLER_SUFFIX = "k";
    private final int ENTITY_EXPIRATION_DAYS = 5;
    private final String LINK_VALIDATION_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_\\+.~#?&\\/\\/=]*)";
}