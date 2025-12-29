package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.ContainerNode;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;

import java.util.List;

/**
 * This class represents a <code>JSON</code> array node.
 * An example for such a node is {@code [1, 2, 3]}.
 */
public class ArrayNode extends ContainerNode<JsonValue> {
    /**
     * This is the delimiter used at the start of every <code>JSON</code> array node.
     */
    public static final char DELIMITER_START = '[';
    /**
     * This is the delimiter used at the end of every <code>JSON</code> array node.
     */
    public static final char DELIMITER_END = ']';
    /**
     * This is the delimiter used between every element of a <code>JSON</code> array node.
     */
    public static final char DELIMITER_ELEMENTS = ',';

    public ArrayNode(final Whitespace whitespace) throws IllegalArgumentException {
        super(whitespace);
    }

    public ArrayNode(final List<JsonValue> elements) throws IllegalArgumentException {
        super(elements);
    }

    @Override
    public char getDelimiterStart() {
        return DELIMITER_START;
    }

    @Override
    public char getDelimiterEnd() {
        return DELIMITER_END;
    }

    @Override
    public char getDelimiterElements() {
        return DELIMITER_ELEMENTS;
    }
}
