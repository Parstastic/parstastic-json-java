package org.parstastic.jparstastic_json.parser.parsers;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonValueParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new JsonValueParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return mergeMaps(
                getSingleValidTargets(),
                getDuplicateValidTargets().entrySet().stream()
                        .map(e -> new AbstractMap.SimpleEntry<>(
                                e.getKey(),
                                e.getValue().getKey()
                        ))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ))
        );
    }

    protected Map<String, JsonParticle> getSingleValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "\"test\"", new JsonValue(new Whitespace(""), new StringNode("test"), new Whitespace("")),
                " \"test\" ", new JsonValue(new Whitespace(" "), new StringNode("test"), new Whitespace(" ")),
                "\"test\" ", new JsonValue(new Whitespace(""), new StringNode("test"), new Whitespace(" ")),
                " \"test\"", new JsonValue(new Whitespace(" "), new StringNode("test"), new Whitespace(""))
        );
    }

    protected Map<String, Map.Entry<JsonParticle, Integer>> getDuplicateValidTargets() throws JsonParticleInstantiationException {
        return getSingleValidTargets().entrySet().stream()
                .map(e -> new AbstractMap.SimpleEntry<>(
                        e.getKey() + e.getKey().trim(),
                        new AbstractMap.SimpleEntry<>(
                                e.getValue(),
                                e.getKey().length()
                        )
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets() {
        return Map.of();
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getParseInvalidTargets() {
        final JsonParsingProcess parsingProcessOneSpace = new JsonParsingProcess(" ");
        parsingProcessOneSpace.incrementIndex();

        final JsonParsingProcess parsingProcessTwoSpaces = new JsonParsingProcess("  ");
        parsingProcessTwoSpaces.incrementIndex();
        parsingProcessTwoSpaces.incrementIndex();

        return mergeMaps(
                super.getParseInvalidTargets(),
                Map.of(
                        "",
                        new JsonParsingResult.JsonParsingResultError(
                                "Exporting failed",
                                new JsonParsingProcess("")
                        ),
                        " ",
                        new JsonParsingResult.JsonParsingResultError(
                                "Exporting failed",
                                parsingProcessOneSpace
                        ),
                        "  ",
                        new JsonParsingResult.JsonParsingResultError(
                                "Exporting failed",
                                parsingProcessTwoSpaces
                        ),
                        "text",
                        new JsonParsingResult.JsonParsingResultError(
                                "Exporting failed",
                                new JsonParsingProcess("text")
                        )
                )
        );
    }
}