package org.parstastic.jparstastic_json.node.null_node;

import org.parstastic.jparstastic_json.node.JsonNode;

/**
 * This class represents a <code>JSON</code> null node.
 * An example for such a node is {@code null}.
 */
public class NullNode extends JsonNode {
    /**
     * This is the value every <code>JSON</code> null node has and every {@link NullNode} object will have.
     */
    public static final String VALUE = "null";

    /**
     * Creates a {@link NullNode} object.
     */
    public NullNode() {
        super();
    }

    @Override
    public String stringify() {
        return VALUE;
    }
}
