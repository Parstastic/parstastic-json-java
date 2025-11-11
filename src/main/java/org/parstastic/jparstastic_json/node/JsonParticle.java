package org.parstastic.jparstastic_json.node;

import static org.parstastic.jparstastic_json.node.object.ObjectNode.ObjectNodeProperty;

/**
 * This abstract class represents anything that can be part of a <code>JSON</code> {@link String}.
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
     * Creates the <code>JSON</code> {@link String} representation of this {@link JsonParticle} object with {@link StringifyOptions#DEFAULT_STRINGIFY_OPTIONS}.
     *
     * @return <code>JSON</code> {@link String}
     */
    public String stringify() {
        return stringify(StringifyOptions.DEFAULT_STRINGIFY_OPTIONS);
    }

    /**
     * Creates the <code>JSON</code> {@link String} representation of this {@link JsonParticle} object according to given {@link StringifyOptions}.
     *
     * @param options {@link StringifyOptions} to stringify with
     * @return <code>JSON</code> {@link String}
     */
    public abstract String stringify(final StringifyOptions options);

    @Override
    public String toString() {
        return this.stringify();
    }
}
