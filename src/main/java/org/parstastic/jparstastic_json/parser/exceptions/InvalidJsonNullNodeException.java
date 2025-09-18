package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.null_node.NullNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link NullNode}.
 */
public class InvalidJsonNullNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonNullNodeException} without a message or a cause
     */
    public InvalidJsonNullNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonNullNodeException} with a message
     */
    public InvalidJsonNullNodeException(final String message) {
        super(message);
    }

    /**
     * Creates an {@link InvalidJsonNullNodeException} with a cause
     */
    public InvalidJsonNullNodeException(final InvalidJsonException cause) {
        super(cause);
    }

    /**
     * Creates an {@link InvalidJsonNullNodeException} with a message and a cause
     */
    public InvalidJsonNullNodeException(final String message, final InvalidJsonException cause) {
        super(message, cause);
    }
}
