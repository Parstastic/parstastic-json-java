package org.parstastic.jparstastic_json.node;

import static org.parstastic.jparstastic_json.node.object.ObjectNode.ObjectNodeProperty;

/**
 * This interface represents anything that can be part of a <code>JSON</code> {@link String}.
 * Included are both nodes and key-value pairs of object nodes.
 *
 * @see JsonNode
 * @see ObjectNodeProperty
 */
public abstract class JsonParticle {
    /**
     * Creates a {@link JsonParticle} object.
     */
    protected JsonParticle() {
        super();
    }

    /**
     * Creates the <code>JSON</code> {@link String} representation of this {@link JsonParticle} object.
     *
     * @return <code>JSON</code> {@link String}
     */
    public abstract String stringify();

    @Override
    public String toString() {
        return this.stringify();
    }
}
