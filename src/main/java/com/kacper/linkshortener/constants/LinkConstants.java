package com.kacper.linkshortener.constants;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LinkConstants {
    private final String CONTROLLER_PREFIX = "li";
    private final String CONTROLLER_SUFFIX = "k";

    public String getCONTROLLER_PREFIX() {
        return CONTROLLER_PREFIX;
    }

}
