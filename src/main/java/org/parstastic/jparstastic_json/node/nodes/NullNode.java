package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.StringifyOptions;

import java.util.Objects;

/**
 * This class represents a <code>JSON</code> null node.
 * An example for such a node is {@code null}.
 */
public class NullNode extends JsonNode {
    /**
     * This is the value every <code>JSON</code> null node has and every {@link NullNode} object will have.
     */
    public static final Object VALUE = null;
    public static final String STRING_VALUE = Objects.toString(VALUE);

    public static final NullNode NULL_NODE = new NullNode();

    /**
     * Creates a {@link NullNode} object.
     */
    private NullNode() {
        super();
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getIndentation() + VALUE;
    }
}
