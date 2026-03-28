package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.ArrayNode;
import org.parstastic.jparstastic_json.node.nodes.BooleanNode;
import org.parstastic.jparstastic_json.node.nodes.NullNode;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.List;
import java.util.Map;

class ArrayNodeParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new ArrayNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "[]", new ArrayNode(new Whitespace("")),
                "[\"hello\",\"world\"]", new ArrayNode(List.of(
                        new JsonValue(new Whitespace(""), new StringNode("hello"), new Whitespace("")),
                        new JsonValue(new Whitespace(""), new StringNode("world"), new Whitespace(""))
                )),
                "[\"string\", true, null, []]", new ArrayNode(List.of(
                        new JsonValue(new Whitespace(""), new StringNode("string"), new Whitespace("")),
                        new JsonValue(new Whitespace(" "), BooleanNode.TRUE, new Whitespace("")),
                        new JsonValue(new Whitespace(" "), NullNode.NULL_NODE, new Whitespace("")),
                        new JsonValue(new Whitespace(" "), new ArrayNode(new Whitespace("")), new Whitespace(""))
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
                "\"[]\"",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("\"[]\"")
                ),
                "{}",
                new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        new JsonParsingProcess("{}")
                )
        );
    }
}