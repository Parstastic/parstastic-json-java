package org.parstastic.jparstastic_json.parser.exceptions;

/**
 * Exceptions of this type are thrown whenever there is a problem when parsing a <code>JSON</code> {@link String}.
 */
public class InvalidJsonException extends RuntimeException {
    /**
     * Creates an {@link InvalidJsonException} without a message
     */
    public InvalidJsonException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonException} with a message
     */
    public InvalidJsonException(final String message) {
        super(message);
    }
}
