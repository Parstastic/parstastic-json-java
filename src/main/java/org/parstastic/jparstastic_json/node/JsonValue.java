package org.parstastic.jparstastic_json.node;

import org.parstastic.jparstastic_json.node.nodes.JsonNode;

public class JsonValue extends JsonParticle {
    private final Whitespace leadingWhitespace;
    private final JsonNode jsonNode;
    private final Whitespace trailingWhitespace;

    public JsonValue(final Whitespace leadingWhitespace, final JsonNode jsonNode, final Whitespace trailingWhitespace)
            throws JsonParticleInstantiationException {
        super();

        validateNotNullOrThrowInstantiationException(leadingWhitespace, "leadingWhitespace");
        validateNotNullOrThrowInstantiationException(jsonNode, "jsonNode");
        validateNotNullOrThrowInstantiationException(trailingWhitespace, "trailingWhitespace");

        this.leadingWhitespace = leadingWhitespace;
        this.jsonNode = jsonNode;
        this.trailingWhitespace = trailingWhitespace;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getJsonValueLeadingWhitespace(this.leadingWhitespace).stringify(options) +
                this.jsonNode.stringify(options) +
                options.getJsonValueTrailingWhitespace(this.trailingWhitespace).stringify(options);
    }
}
