package org.parstastic.jparstastic_json.node.null_node;

import org.parstastic.jparstastic_json.node.builders.JsonNodeFullTextMatchingBuilder;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonNullNodeException;

import java.util.Set;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link NullNode} objects.
 */
public class NullNodeBuilder extends JsonNodeFullTextMatchingBuilder<NullNode, InvalidJsonNullNodeException, String> {
    /**
     * Creates a {@link NullNodeBuilder} object.
     */
    public NullNodeBuilder() {
        super(Set.of(NullNode.VALUE));
    }

    /**
     * Creates a {@link NullNode} object.
     *
     * @param value the value of the {@link NullNode} object, will always be {@link NullNode#VALUE}
     * @return a {@link NullNode} object
     */
    @Override
    protected NullNode createNode(final String value) {
        return new NullNode();
    }

    /**
     * Creates an {@link InvalidJsonNullNodeException}
     *
     * @return an {@link InvalidJsonNullNodeException}
     */
    @Override
    protected InvalidJsonNullNodeException createException() {
        return new InvalidJsonNullNodeException();
    }
}
