package org.parstastic.jparstastic_json.node.array;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.StringifyOptions;

import java.util.List;

/**
 * This class represents a <code>JSON</code> array node.
 * An example for such a node is {@code [1, 2, 3]}.
 */
public class ArrayNode extends JsonNode {
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

    /**
     * {@link List} containing all the elements of the <code>JSON</code> array node.
     */
    private final List<JsonNode> elements;

    /**
     * Creates an {@link ArrayNode} object with the given {@code elements}.
     *
     * @param elements elements of the <code>JSON</code> array node
     */
    public ArrayNode(final List<JsonNode> elements) {
        super();
        this.elements = elements;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(options.getIndentation()).append(DELIMITER_START).append(options.getLineBreak());
        for (int i = 0; i < this.elements.size(); i++) {
            stringBuilder.append(this.elements.get(i).stringify(options.withIncreasedIndentationLevel()));
            if (i < this.elements.size() - 1) {
                stringBuilder.append(DELIMITER_ELEMENTS);
            }
            stringBuilder.append(options.getLineBreak());
        }
        stringBuilder.append(options.getIndentation()).append(DELIMITER_END);
        return stringBuilder.toString();
    }
}
