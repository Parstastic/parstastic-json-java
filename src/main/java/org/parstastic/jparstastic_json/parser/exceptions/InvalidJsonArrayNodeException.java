package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.array.ArrayNode;

/**
 * Exception thrown whenever there is a problem parsing an {@link ArrayNode}.
 */
public class InvalidJsonArrayNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonArrayNodeException} without a message
     */
    public InvalidJsonArrayNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonArrayNodeException} with a message
     */
    public InvalidJsonArrayNodeException(final String message) {
        super(message);
    }
}
