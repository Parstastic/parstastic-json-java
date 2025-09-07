package org.parstastic.parser.exceptions;

import org.parstastic.node.object.ObjectNode;

/**
 * Exception thrown whenever there is a problem parsing an {@link ObjectNode}.
 */
public class InvalidJsonObjectNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonObjectNodeException} without a message
     */
    public InvalidJsonObjectNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonObjectNodeException} with a message
     */
    public InvalidJsonObjectNodeException(final String message) {
        super(message);
    }
}
