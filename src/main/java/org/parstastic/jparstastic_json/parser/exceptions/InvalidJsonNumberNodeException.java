package org.parstastic.jparstastic_json.parser.exceptions;

import org.parstastic.jparstastic_json.node.nodes.NumberNode;

/**
 * Exception thrown whenever there is a problem parsing a {@link NumberNode}.
 */
public class InvalidJsonNumberNodeException extends InvalidJsonException {
    /**
     * Creates an {@link InvalidJsonNumberNodeException} without a message or a cause
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

    /**
     * Creates an {@link InvalidJsonNumberNodeException} with a cause
     */
    public InvalidJsonNumberNodeException(final InvalidJsonException cause) {
        super(cause);
    }

    /**
     * Creates an {@link InvalidJsonNumberNodeException} with a message and a cause
     */
    public InvalidJsonNumberNodeException(final String message, final InvalidJsonException cause) {
        super(message, cause);
    }
}
