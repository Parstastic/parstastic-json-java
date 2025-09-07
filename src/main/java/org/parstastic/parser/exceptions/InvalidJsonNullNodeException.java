package org.parstastic.parser.exceptions;

import org.parstastic.node.null_node.NullNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link NullNode}.
 */
public class InvalidJsonNullNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonNullNodeException} without a message
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
}
