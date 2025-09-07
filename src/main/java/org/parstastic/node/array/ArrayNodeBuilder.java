package org.parstastic.node.array;

import org.parstastic.node.JsonNode;
import org.parstastic.node.builders.JsonNodeWithInnerDelimitersBuilder;
import org.parstastic.parser.JsonParser;
import org.parstastic.parser.exceptions.InvalidJsonArrayNodeException;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link ArrayNode} objects.
 */
public class ArrayNodeBuilder extends JsonNodeWithInnerDelimitersBuilder<ArrayNode, InvalidJsonArrayNodeException, JsonNode> {
    /**
     * Creates an {@link ArrayNodeBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    public ArrayNodeBuilder(final JsonParser jsonParser) {
        super(jsonParser, ArrayNode.DELIMITER_START, ArrayNode.DELIMITER_END, ArrayNode.DELIMITER_ELEMENTS, jsonParser);
    }

    /**
     * Creates an {@link ArrayNode} object with parsed {@link #elements}.
     *
     * @return an {@link ArrayNode} object with parsed {@link #elements}
     */
    @Override
    protected ArrayNode createNode() {
        return new ArrayNode(this.elements);
    }

    /**
     * Creates an {@link InvalidJsonArrayNodeException}.
     *
     * @return an {@link InvalidJsonArrayNodeException}
     */
    @Override
    protected InvalidJsonArrayNodeException createException() {
        return new InvalidJsonArrayNodeException();
    }
}
