package org.parstastic.jparstastic_json.node.string;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonStringNodeException;

import java.util.List;

class StringNodeBuilderTest extends JsonParserTest<StringNode, InvalidJsonStringNodeException, StringNodeBuilder> {
    @Override
    protected StringNodeBuilder getParser() {
        return new StringNodeBuilder();
    }

    @Override
    protected Class<StringNode> getParticleType() {
        return StringNode.class;
    }

    @Override
    protected Class<InvalidJsonStringNodeException> getExceptionType() {
        return InvalidJsonStringNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "\"Hello World!\"",
                "        \"Hello World!\"    "
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "\"Hello World!",
                "\"\\q\""
        );
    }
}