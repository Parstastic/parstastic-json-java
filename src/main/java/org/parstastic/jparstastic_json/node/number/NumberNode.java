package org.parstastic.jparstastic_json.node.number;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.StringifyOptions;

/**
 * This class represents a <code>JSON</code> number node.
 * An example for such a node is {@code 18.8}.
 */
public class NumberNode extends JsonNode {
    /**
     * Character delimiter between the integer and decimal parts of the <code>JSON</code> number node.
     */
    public static final char DECIMAL_DELIMITER = '.';

    /**
     * Numeric value of the <code>JSON</code> number node
     */
    private final Number value;

    /**
     * Creates a {@link NumberNode} object with the given {@code value} as numeric value.
     *
     * @param value numeric value of the <code>JSON</code> number node
     */
    public NumberNode(final Number value) {
        super();
        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getIndentation() + this.value.toString();
    }
}
