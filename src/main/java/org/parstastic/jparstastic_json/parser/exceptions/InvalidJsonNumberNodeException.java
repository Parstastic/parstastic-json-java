package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.number.NumberNode;

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
