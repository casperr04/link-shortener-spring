package com.kacper.linkshortener.constants;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
@Getter
@Setter
@RequiredArgsConstructor
public class LinkConstants {
    private final String CONTROLLER_PREFIX = "li";
    private final String CONTROLLER_SUFFIX = "k";
    @Value("${link_expiry_days}")
    private int ENTITY_EXPIRATION_DAYS;
    @Value("${link_expiry_hours}")
    private int ENTITY_EXPIRATION_HOURS;
    @Value("${link_expiry_minutes}")
    private int ENTITY_EXPIRATION_MINUTES;
    private final String LINK_VALIDATION_REGEX = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()!@:%_\\+.~#?&\\/\\/=]*)";
}