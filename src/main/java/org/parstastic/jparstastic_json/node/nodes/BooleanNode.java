package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.StringifyOptions;

import java.util.Set;

/**
 * This class represents a <code>JSON</code> boolean node.
 * An example for such a node is {@code true}.
 */
public class BooleanNode extends JsonNode {
    public static final BooleanNode TRUE;
    public static final BooleanNode FALSE;
    public static final Set<BooleanNode> VALUES;

    static {
        try {
            TRUE = new BooleanNode(true);
            FALSE = new BooleanNode(false);
            VALUES = Set.of(
                    TRUE,
                    FALSE
            );
        } catch (final JsonParticleInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Value of the <code>JSON</code> boolean node
     */
    private final boolean value;

    /**
     * Creates a {@link BooleanNode} object with given {@code value}.
     *
     * @param value {@link Boolean} value
     */
    private BooleanNode(final boolean value) throws JsonParticleInstantiationException {
        super();

        validateNotNullOrThrowInstantiationException(value, "value");

        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return Boolean.toString(this.value);
    }

    public boolean hasValue(final Boolean value) {
        return value != null && value == this.value;
    }
}
