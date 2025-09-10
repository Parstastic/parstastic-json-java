package org.parstastic.jparstastic_json.node.boolean_node;

import org.parstastic.jparstastic_json.node.builders.JsonNodeFullTextMatchingBuilder;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonBooleanNodeException;

import java.util.Set;

import static org.parstastic.jparstastic_json.node.boolean_node.BooleanNode.BooleanValue;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link BooleanNode} objects.
 */
public class BooleanNodeBuilder extends JsonNodeFullTextMatchingBuilder<BooleanNode, InvalidJsonBooleanNodeException, BooleanValue> {
    /**
     * Creates a {@link BooleanNodeBuilder} object.
     */
    public BooleanNodeBuilder() {
        super(Set.of(BooleanValue.TRUE, BooleanValue.FALSE));
    }

    /**
     * Creates a {@link BooleanNode} object with a {@link BooleanValue}.
     *
     * @param value the {@link BooleanValue} of the {@link BooleanNode} object
     * @return a {@link BooleanNode} object with a {@link BooleanValue}
     */
    @Override
    protected BooleanNode createNode(final BooleanValue value) {
        return new BooleanNode(value);
    }

    /**
     * Creates an {@link InvalidJsonBooleanNodeException}.
     *
     * @return an {@link InvalidJsonBooleanNodeException}
     */
    @Override
    protected InvalidJsonBooleanNodeException createException() {
        return new InvalidJsonBooleanNodeException();
    }
}
