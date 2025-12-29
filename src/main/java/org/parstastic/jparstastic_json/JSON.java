package org.parstastic.jparstastic_json;

import org.parstastic.jparstastic_json.node.JsonNode;

public final class JSON {
    private JSON() throws IllegalStateException {
        throw new IllegalStateException();
    }

    public static String stringify(final JsonNode jsonNode) {
        return jsonNode.stringify();
    }

    public static JsonNode parse(final String json) {
        throw new UnsupportedOperationException();
    }
}
