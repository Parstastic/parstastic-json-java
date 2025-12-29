package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;

/**
 * This class encapsulates all common behavior of different types of <code>JSON</code> nodes.
 */
public abstract class JsonNode extends JsonParticle {
    /**
     * Creates a {@link JsonNode} object.
     */
    protected JsonNode() {
        super();
    }
}
