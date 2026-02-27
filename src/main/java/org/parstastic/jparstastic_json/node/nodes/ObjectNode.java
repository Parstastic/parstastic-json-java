package org.parstastic.jparstastic_json.node.nodes;

import org.parstastic.jparstastic_json.node.*;

import java.util.List;

/**
 * This class represents a <code>JSON</code> object node.
 * An example for such a node is {@code {"foo": "bar"}}.
 */
public class ObjectNode extends ContainerNode<ObjectNode.ObjectNodeProperty> {
    /**
     * This class represents a <code>JSON</code> object node's property.
     * It is a key-value pair.
     */
    public static class ObjectNodeProperty extends JsonParticle {
        /**
         * This is the delimiter used between the key and the value of every {@link ObjectNodeProperty}.
         */
        public static final char KEY_VALUE_DELIMITER = ':';

        private final Whitespace leadingWhitespace;
        private final StringNode key;
        private final Whitespace trailingWhitespace;
        private final JsonValue value;

        public ObjectNodeProperty(final Whitespace leadingWhitespace,
                                   final StringNode key,
                                   final Whitespace trailingWhitespace,
                                   final JsonValue value) {
            super();
            this.leadingWhitespace = leadingWhitespace;
            this.key = key;
            this.trailingWhitespace = trailingWhitespace;
            this.value = value;
        }

        @Override
        public String stringify(final StringifyOptions options) {
            return options.getIndentation() + key + KEY_VALUE_DELIMITER + value.stringify(options.skipFirstIndentation());
        }
    }

    /**
     * This is the delimiter used at the start of every <code>JSON</code> object node.
     */
    public static final char DELIMITER_START = '{';
    /**
     * This is the delimiter used at the end of every <code>JSON</code> object node.
     */
    public static final char DELIMITER_END = '}';
    /**
     * This is the delimiter used between every element of a <code>JSON</code> object node.
     */
    public static final char DELIMITER_ELEMENTS = ',';

    public ObjectNode(final Whitespace whitespace) throws JsonParticleInstantiationException {
        super(whitespace);
    }

    public ObjectNode(final List<ObjectNodeProperty> elements) throws JsonParticleInstantiationException {
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
