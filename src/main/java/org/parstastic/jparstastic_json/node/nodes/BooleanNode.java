package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
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

    public static final BooleanNode TRUE;
    public static final BooleanNode FALSE;

    static {
        try {
            TRUE = new BooleanNode(BooleanValue.TRUE);
            FALSE = new BooleanNode(BooleanValue.FALSE);
        } catch (final JsonParticleInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Value of the <code>JSON</code> boolean node
     */
    private final BooleanValue value;

    /**
     * Creates a {@link BooleanNode} object with given {@code value}.
     *
     * @param value {@link BooleanValue} value
     */
    private BooleanNode(final BooleanValue value) throws JsonParticleInstantiationException {
        super();

        validateNotNullOrThrowInstantiationException(value, "value");

        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return this.value.toString();
    }
}
