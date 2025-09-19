package org.parstastic.jparstastic_json.node.array;

import org.parstastic.jparstastic_json.parser.JsonParserTest;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonArrayNodeException;

import java.util.List;

class ArrayNodeBuilderTest extends JsonParserTest<ArrayNode, InvalidJsonArrayNodeException, ArrayNodeBuilder> {

    @Override
    protected ArrayNodeBuilder getParser() {
        return new ArrayNodeBuilder();
    }

    @Override
    protected Class<ArrayNode> getParticleType() {
        return ArrayNode.class;
    }

    @Override
    protected Class<InvalidJsonArrayNodeException> getExceptionType() {
        return InvalidJsonArrayNodeException.class;
    }

    @Override
    protected List<String> getValidJsonStrings() {
        return List.of(
                "[\"Hello\", \"World\", \"!\"]",
                "\t [\"Hello\", \"World\", \"!\"]\n",
                "\t [\"Hello\", \"World\", \"!\"                 ]",
                "[]",
                "[     ]",
                "[true, false]",
                "[null, \"Hello World!\", [\"Another array\", \"\\\"\"]]",
                "[1, 2.0]"
        );
    }

    @Override
    protected List<String> getInvalidJsonStrings() {
        return List.of(
                "[,\"text\"]",
                "[\"text\",]",
                "[,]"
        );
    }
}