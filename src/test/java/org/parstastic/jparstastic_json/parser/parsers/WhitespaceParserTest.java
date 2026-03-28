package org.parstastic.jparstastic_json.parser.parsers;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Map;

class WhitespaceParserTest extends JsonParticleParserTest {
    @Override
    protected JsonParticleParser<?> getInstance() {
        return new WhitespaceParser();
    }

    @Override
    protected Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException {
        return Map.of(
                "", new Whitespace(""),
                " ", new Whitespace(" "),
                "\t", new Whitespace("\t"),
                "\n", new Whitespace("\n"),
                "\r", new Whitespace("\r"),
                " \t\n\r", new Whitespace(" \t\n\r"),
                "\"\"", new Whitespace(""),
                "\" \"", new Whitespace("")
        );
    }

    @Override
    protected Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets() {
        return Map.of();
    }
}