package org.parstastic.node;

/**
 * This class encapsulates all common behavior of different types of <code>JSON</code> nodes.
 */
public abstract class JsonNode {
    /**
     * Creates a {@link JsonNode} object.
     */
    protected JsonNode() {
        super();
    }

    /**
     * Creates the <code>JSON</code> string representation of this node.
     *
     * @return <code>JSON</code> string
     */
    public abstract String stringify();

    @Override
    public String toString() {
        return this.stringify();
    }
}
