package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.Map;
import java.util.stream.IntStream;

class StringNodeParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new StringNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "\"Hello World\"", new StringNode("Hello World"),
                "\"\\u2468\"", new StringNode("\\u2468"),
                "\"\\\"\"", new StringNode("\\\""),
                "\"\\\"Hello World\\\"\"", new StringNode("\\\"Hello World\\\"")
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
                "Hello World",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("Hello World")
                )
        );
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getParseInvalidTargets() {
        final JsonParsingProcess invalidEscape = new JsonParsingProcess("\"\\\"");
        IntStream.range(0, 3).forEach(i -> invalidEscape.incrementIndex());

        final JsonParsingProcess invalidUnicode = new JsonParsingProcess("\"\\u246\"");
        IntStream.range(0, 6).forEach(i -> invalidUnicode.incrementIndex());

        return mergeMaps(
                super.getParseInvalidTargets(),
                Map.of(
                        "\"\\\"",
                        new JsonParsingResult.JsonParsingResultError(
                                "The JsonParsingProcess has exceeded its JSON's length.",
                                invalidEscape
                        ),
                        "\"\\u246\"",
                        new JsonParsingResult.JsonParsingResultError(
                                "The character to validate was not found or invalid.",
                                invalidUnicode
                        )
                )
        );
    }
}