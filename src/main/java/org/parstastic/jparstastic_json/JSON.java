package org.parstastic.jparstastic_json;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.FullStringJsonParser;

public final class JSON {
    private JSON() throws IllegalStateException {
        throw new IllegalStateException();
    }

    public static String stringify(final JsonNode jsonNode) {
        return jsonNode.stringify();
    }

    public static JsonNode parse(final String json) {
        return new FullStringJsonParser().parseJson(json);
    }
}
