package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.nodes.NullNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.Map;

class NullNodeParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new NullNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() {
        return Map.of(
                "null", NullNode.NULL_NODE
        );
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets() {
        return Map.of(
                "",
                new JsonParsingResult.JsonParsingResultError(
                        "The JsonParsingProcess has exceeded its JSON's length.",
                        new JsonParsingProcess("")
                ),
                "\"null\"",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("\"null\"")
                )
        );
    }
}