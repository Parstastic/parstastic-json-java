package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.ObjectNode;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.List;
import java.util.Map;

class ObjectNodeParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new ObjectNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "{}", new ObjectNode(new Whitespace("")),
                "{\"hello\":\"world\"}", new ObjectNode(List.of(
                        new ObjectNode.ObjectNodeProperty(
                                new Whitespace(""),
                                new StringNode("hello"),
                                new Whitespace(""),
                                new JsonValue(
                                        new Whitespace(""),
                                        new StringNode("world"),
                                        new Whitespace("")
                                )
                        )
                ))
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
                "\"{}\"",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("\"{}\"")
                ),
                "[]",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("[]")
                )
        );
    }
}