package org.parstastic.jparstastic_json.node;

import org.parstastic.jparstastic_json.node.nodes.JsonNode;

public class JsonValue extends JsonParticle {
    private final Whitespace leadingWhitespace;
    private final JsonNode jsonNode;
    private final Whitespace trailingWhitespace;

    public JsonValue(final Whitespace leadingWhitespace, final JsonNode jsonNode, final Whitespace trailingWhitespace) {
        super();
        this.leadingWhitespace = leadingWhitespace;
        this.jsonNode = jsonNode;
        this.trailingWhitespace = trailingWhitespace;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return this.jsonNode.stringify(options);
    }
}
