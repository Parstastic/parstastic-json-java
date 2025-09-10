package org.parstastic.jparstastic_json.node.number;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonNumberNodeException;

import java.util.List;

class NumberNodeBuilderTest extends JsonParserTest<NumberNode, InvalidJsonNumberNodeException, NumberNodeBuilder> {
    @Override
    protected NumberNodeBuilder getParser() {
        return new NumberNodeBuilder();
    }

    @Override
    protected Class<NumberNode> getParticleType() {
        return NumberNode.class;
    }

    @Override
    protected Class<InvalidJsonNumberNodeException> getExceptionType() {
        return InvalidJsonNumberNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "10",
                "15.5"
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "\"Hello World!\""
        );
    }
}