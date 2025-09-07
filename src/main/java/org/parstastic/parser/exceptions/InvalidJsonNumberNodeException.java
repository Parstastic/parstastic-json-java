package org.parstastic.parser.exceptions;

import org.parstastic.node.number.NumberNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link NumberNode}.
 */
public class InvalidJsonNumberNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonNumberNodeException} without a message
     */
    public InvalidJsonNumberNodeException() {
        super();
    }

    /**
     * Creates an {@link InvalidJsonNumberNodeException} with a message
     */
    public InvalidJsonNumberNodeException(final String message) {
        super(message);
    }
}
