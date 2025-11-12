package org.parstastic.jparstastic_json.node.object;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonObjectNodeException;

import java.util.List;

class ObjectNodeBuilderTest extends JsonParserTest<ObjectNode, InvalidJsonObjectNodeException, ObjectNodeBuilder> {
    @Override
    protected ObjectNodeBuilder getParser() {
        return new ObjectNodeBuilder();
    }

    @Override
    protected Class<ObjectNode> getParticleType() {
        return ObjectNode.class;
    }

    @Override
    protected Class<InvalidJsonObjectNodeException> getExceptionType() {
        return InvalidJsonObjectNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "{\"hello\":\"world\"}",
                "  {   \"hello\"   :   \r \"world\"  \n}",
                "{}"
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "{",
                "{\"hello\"}",
                "{\"hello\":}"
        );
    }
}