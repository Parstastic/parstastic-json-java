package org.parstastic.jparstastic_json.node.null_node;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonNullNodeException;

import java.util.List;

class NullNodeBuilderTest extends JsonParserTest<NullNode, InvalidJsonNullNodeException, NullNodeBuilder> {
    @Override
    protected NullNodeBuilder getParser() {
        return new NullNodeBuilder();
    }

    @Override
    protected Class<NullNode> getParticleType() {
        return NullNode.class;
    }

    @Override
    protected Class<InvalidJsonNullNodeException> getExceptionType() {
        return InvalidJsonNullNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "null"
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "\"Hello World!\""
        );
    }
}