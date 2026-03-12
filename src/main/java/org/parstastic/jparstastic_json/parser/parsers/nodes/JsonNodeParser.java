package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.nodes.JsonNode;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;

public abstract class JsonNodeParser<J extends JsonNode> extends JsonParticleParser<J> {
    protected JsonNodeParser() {
        super();
    }
}
