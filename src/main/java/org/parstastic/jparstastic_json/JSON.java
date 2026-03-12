package org.parstastic.jparstastic_json;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.parser.FullStringJsonParser;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

public final class JSON {
    private JSON() throws IllegalStateException {
        throw new IllegalStateException();
    }

    public static String stringify(final JsonParticle jsonParticle) {
        return jsonParticle.stringify();
    }

    public static JsonValue parse(final String json) {
        try {
            return new FullStringJsonParser().parse(json).getValue();
        } catch (final JsonParsingResult.JsonParsingResultNoSuchElementException e) {
            throw new RuntimeException(e);
        }
    }
}
