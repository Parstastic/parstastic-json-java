package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.NumberNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParserTest;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

class NumberNodeParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new NumberNodeParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "-1", new NumberNode(-1L),
                "1", new NumberNode(1L),
                "0.5", new NumberNode(0.5),
                "25", new NumberNode(25L),
                "100E100", new NumberNode(100L, true, NumberNode.NumberNodeExponentSignSymbol.BLANK, 100),
                "100E+100", new NumberNode(100L, true, NumberNode.NumberNodeExponentSignSymbol.PLUS, 100)
        );
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets() {
        Map<String, JsonParsingResult.JsonParsingResultError> validTargetsAsStrings;
        try {
            validTargetsAsStrings = getValidTargets().keySet().stream()
                    .map(json -> {
                        final String newJson = "\"" + json + "\"";
                        return new AbstractMap.SimpleEntry<>(
                                newJson,
                                new JsonParsingResult.JsonParsingResultError(
                                        "Exporting failed",
                                        new JsonParsingProcess(newJson)
                                )
                        );
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
        } catch (JsonParticleInstantiationException e) {
            validTargetsAsStrings = new HashMap<>();
        }

        final Map<String, JsonParsingResult.JsonParsingResultError> invalidTargets = Map.of(
                "",
                new JsonParsingResult.JsonParsingResultError(
                        "Exporting failed",
                        new JsonParsingProcess("")
                )
        );

        return mergeMaps(invalidTargets, validTargetsAsStrings);
    }
}