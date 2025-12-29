package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.nodes.ArrayNode;

/**
 * Exception thrown whenever there is a problem parsing an {@link ArrayNode}.
 */
public class InvalidJsonArrayNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonArrayNodeException} without a message or a cause
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

    /**
     * Creates an {@link InvalidJsonArrayNodeException} with a cause
     */
    public InvalidJsonArrayNodeException(final InvalidJsonException cause) {
        super(cause);
    }

    /**
     * Creates an {@link InvalidJsonArrayNodeException} with a message and a cause
     */
    public InvalidJsonArrayNodeException(final String message, final InvalidJsonException cause) {
        super(message, cause);
    }
}
