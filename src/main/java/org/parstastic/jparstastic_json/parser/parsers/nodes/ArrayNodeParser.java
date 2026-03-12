package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.ArrayNode;
import org.parstastic.jparstastic_json.parser.parsers.JsonParticleParser;
import org.parstastic.jparstastic_json.parser.parsers.JsonValueParser;

import java.util.List;

public class ArrayNodeParser extends ContainerNodeParser<JsonValue, ArrayNode> {
    public ArrayNodeParser() {
        super();
    }

    @Override
    protected char getStartDelimiter() {
        return ArrayNode.DELIMITER_START;
    }

    @Override
    protected char getEndDelimiter() {
        return ArrayNode.DELIMITER_END;
    }

    @Override
    protected char getElementDelimiter() {
        return ArrayNode.DELIMITER_ELEMENTS;
    }

    @Override
    protected JsonParticleParser<JsonValue> getElementParser() {
        return new JsonValueParser();
    }

    @Override
    protected JsonParticleParser<JsonValue> getElementParser(final Whitespace whitespace) {
        return new JsonValueParser(whitespace);
    }

    @Override
    protected ArrayNode create(final Whitespace whitespace) throws JsonParticleInstantiationException {
        return new ArrayNode(whitespace);
    }

    @Override
    protected ArrayNode create(final List<JsonValue> elements) throws JsonParticleInstantiationException {
        return new ArrayNode(elements);
    }
}
