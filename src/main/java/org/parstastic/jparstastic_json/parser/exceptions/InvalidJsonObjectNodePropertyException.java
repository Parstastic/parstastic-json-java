package org.parstastic.jparstastic_json.parser.exceptions;

import static org.parstastic.jparstastic_json.node.nodes.ObjectNode.ObjectNodeProperty;

/**
 * Exception thrown whenever there is a problem parsing an {@link ObjectNodeProperty}.
 */
public class InvalidJsonObjectNodePropertyException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonObjectNodePropertyException} without a message or a cause
     */
    public InvalidJsonObjectNodePropertyException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonObjectNodePropertyException} with a message
     */
    public InvalidJsonObjectNodePropertyException(final String message) {
        super(message);
    }

    /**
     * Creates an {@link InvalidJsonObjectNodePropertyException} with a cause
     */
    public InvalidJsonObjectNodePropertyException(final InvalidJsonException cause) {
        super(cause);
    }

    /**
     * Creates an {@link InvalidJsonObjectNodePropertyException} with a message and a cause
     */
    public InvalidJsonObjectNodePropertyException(final String message, final InvalidJsonException cause) {
        super(message, cause);
    }
}
