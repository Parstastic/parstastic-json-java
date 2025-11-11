package org.parstastic.jparstastic_json.node.string;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.StringifyOptions;

/**
 * This class represents a <code>JSON</code> string node.
 * An example for such a node is {@code "Hello, World!"}.
 */
public class StringNode extends JsonNode {
    /**
     * This is the delimiter used before and after every <code>JSON</code> string node.
     */
    public static final char DELIMITER = '"';

    /**
     * This is the text value of the <code>JSON</code> string node.
     */
    private final String value;

    /**
     * Creates a {@link StringNode} object with the given {@code value} as text value.
     *
     * @param value text of the <code>JSON</code> string node
     */
    public StringNode(final String value) {
        super();
        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getIndentation() + DELIMITER + this.value + DELIMITER;
    }

    /**
     * Returns the text value of the <code>JSON</code> string node.
     *
     * @return text value of the <code>JSON</code> string node
     */
    public String getValue() {
        return this.value;
    }
}
