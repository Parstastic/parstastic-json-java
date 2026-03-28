package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.nodes.BooleanNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.Map;

class BooleanNodeParserTest extends JsonParticleParserTest {

    @Override
    protected JsonParticleParser<?> getInstance() {
        return new BooleanNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() {
        return Map.of(
                "true", BooleanNode.TRUE,
                "false", BooleanNode.FALSE
        );
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets() {
        return Map.of(
                "\"true\"",
                new JsonParsingResult.JsonParsingResultError(
                        "Exporting failed",
                        new JsonParsingProcess("\"true\"")
                ),
                "\"false\"",
                new JsonParsingResult.JsonParsingResultError(
                        "Exporting failed",
                        new JsonParsingProcess("\"false\"")
                )
        );
    }
}