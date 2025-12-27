package org.parstastic.jparstastic_json.node.boolean_node;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.StringifyOptions;

/**
 * This class represents a <code>JSON</code> boolean node.
 * An example for such a node is {@code true}.
 */
public class BooleanNode extends JsonNode {
    /**
     * This enum contains all possible values a <code>JSON</code> boolean node may have.
     */
    public enum BooleanValue {
        /**
         * <code>JSON</code> boolean node value for {@code true}
         */
        TRUE(true),
        /**
         * <code>JSON</code> boolean node value for {@code false}
         */
        FALSE(false);

        /**
         * Corresponding {@link Boolean} value of the {@link BooleanValue}
         */
        private final boolean value;

        /**
         * Creates a {@link BooleanValue} object with corresponding {@link Boolean} {@code value}.
         *
         * @param value corresponding {@link Boolean} {@code value}
         */
        BooleanValue(final boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return Boolean.toString(this.value);
        }
    }

    public static final BooleanNode TRUE = new BooleanNode(BooleanValue.TRUE);
    public static final BooleanNode FALSE = new BooleanNode(BooleanValue.FALSE);

    /**
     * Value of the <code>JSON</code> boolean node
     */
    private final BooleanValue value;

    /**
     * Creates a {@link BooleanNode} object with given {@code value}.
     *
     * @param value {@link BooleanValue} value
     */
    private BooleanNode(final BooleanValue value) {
        super();
        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return options.getIndentation() + this.value.toString();
    }
}
