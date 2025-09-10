package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;

/**
 * Exception thrown whenever there is a problem with a {@link JsonParsingProcess}.
 */
public class JsonParsingProcessException extends RuntimeException {
    /**
     * Creates a {@link JsonParsingProcessException}
     */
    public JsonParsingProcessException() {
        super();
    }
}
