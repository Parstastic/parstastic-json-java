package org.parstastic.parser.exceptions;

import org.parstastic.node.boolean_node.BooleanNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link BooleanNode}.
 */
public class InvalidJsonBooleanNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonBooleanNodeException} without a message
     */
    public InvalidJsonBooleanNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonBooleanNodeException} with a message
     */
    public InvalidJsonBooleanNodeException(final String message) {
        super(message);
    }
}
