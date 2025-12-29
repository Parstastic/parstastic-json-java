package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.nodes.StringNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link StringNode}.
 */
public class InvalidJsonStringNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonStringNodeException} without a message or a cause
     */
    public InvalidJsonStringNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonStringNodeException} with a message
     */
    public InvalidJsonStringNodeException(final String message) {
        super(message);
    }

    /**
     * Creates an {@link InvalidJsonStringNodeException} with a cause
     */
    public InvalidJsonStringNodeException(final InvalidJsonException cause) {
        super(cause);
    }

    /**
     * Creates an {@link InvalidJsonStringNodeException} with a message and a cause
     */
    public InvalidJsonStringNodeException(final String message, final InvalidJsonException cause) {
        super(message, cause);
    }
}
