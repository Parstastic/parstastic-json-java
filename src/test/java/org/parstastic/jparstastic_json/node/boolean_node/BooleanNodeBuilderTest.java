package org.parstastic.jparstastic_json.node.boolean_node;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonBooleanNodeException;

import java.util.List;

class BooleanNodeBuilderTest extends JsonParserTest<BooleanNode, InvalidJsonBooleanNodeException, BooleanNodeBuilder> {

    @Override
    protected BooleanNodeBuilder getParser() {
        return new BooleanNodeBuilder();
    }

    @Override
    protected Class<BooleanNode> getParticleType() {
        return BooleanNode.class;
    }

    @Override
    protected Class<InvalidJsonBooleanNodeException> getExceptionType() {
        return InvalidJsonBooleanNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "true",
                "false"
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "\"Hello World!\""
        );
    }
}