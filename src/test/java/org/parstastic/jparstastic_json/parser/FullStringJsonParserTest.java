package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonValueParserTest;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FullStringJsonParserTest extends JsonValueParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new FullStringJsonParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return getSingleValidTargets();
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getParseInvalidTargets() {
        Map<String, JsonParsingResult.JsonParsingResultError> duplicateValidTargetsNowInvalid;
        try {
            duplicateValidTargetsNowInvalid = getDuplicateValidTargets().entrySet().stream()
                    .map(e -> {
                        final JsonParsingProcess parsingProcess = new JsonParsingProcess(e.getKey());
                        IntStream.range(0, e.getValue().getValue()).forEach(i -> parsingProcess.incrementIndex());
                        return new AbstractMap.SimpleEntry<>(
                                e.getKey(),
                                new JsonParsingResult.JsonParsingResultError(
                                        "The JSON String is not fully parsed.",
                                        parsingProcess
                                )
                        );
                    })
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
        } catch (final JsonParticleInstantiationException e) {
            duplicateValidTargetsNowInvalid = new HashMap<>();
        }

        final JsonParsingProcess parsingProcessNullNull = new JsonParsingProcess("null null");
        IntStream.range(0, 5).forEach(i -> parsingProcessNullNull.incrementIndex());

        final JsonParsingProcess parsingProcessObjectWithText = new JsonParsingProcess("{\"hello\":\"world\"} text");
        IntStream.range(0, 18).forEach(i -> parsingProcessObjectWithText.incrementIndex());

        return mergeMaps(
                super.getParseInvalidTargets(),
                duplicateValidTargetsNowInvalid,
                Map.of(
                        "null null",
                        new JsonParsingResult.JsonParsingResultError(
                                "The JSON String is not fully parsed.",
                                parsingProcessNullNull
                        ),
                        "{\"hello\":\"world\"} text",
                        new JsonParsingResult.JsonParsingResultError(
                                "The JSON String is not fully parsed.",
                                parsingProcessObjectWithText
                        )
                )
        );
    }
}