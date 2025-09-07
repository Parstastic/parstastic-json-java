package org.parstastic.node.object;

import org.parstastic.node.JsonParticle;
import org.parstastic.node.JsonNode;
import org.parstastic.node.string.StringNode;

import java.util.List;

/**
 * This class represents a <code>JSON</code> object node.
 * An example for such a node is {@code {"foo": "bar"}}.
 */
public class ObjectNode extends JsonNode {
    /**
     * This class represents a <code>JSON</code> object node's property.
     * It is a key-value pair.
     */
    public static class ObjectNodeProperty extends JsonParticle {
        /**
         * This is the delimiter used before and after the key of every {@link ObjectNodeProperty}.
         *
         * @implNote equal to {@link StringNode#DELIMITER}
         */
        public static final char DELIMITER_KEY = StringNode.DELIMITER;
        /**
         * This is the delimiter used between the key and the value of every {@link ObjectNodeProperty}.
         */
        public static final char DELIMITER = ':';

        private final String key;
        private final JsonNode value;

        /**
         * Creates an {@link ObjectNodeProperty} object with specified key and value.
         *
         * @param key key of the property
         * @param value value of the property
         */
        public ObjectNodeProperty(final String key, final JsonNode value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String stringify() {
            return DELIMITER_KEY + key + DELIMITER_KEY + DELIMITER + value.toString();
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

    /**
     * {@link List} containing the properties of the <code>JSON</code> object node.
     */
    private final List<ObjectNodeProperty> properties;

    /**
     * Creates an {@link ObjectNode} object with the given {@code properties}.
     *
     * @param properties properties of the <code>JSON</code> object node
     */
    public ObjectNode(final List<ObjectNodeProperty> properties) {
        super();
        this.properties = properties;
    }

    @Override
    public String stringify() {
        final List<String> props = this.properties.stream()
                .map(Object::toString)
                .toList();
        return DELIMITER_START + String.join(String.valueOf(DELIMITER_ELEMENTS), props) + DELIMITER_END;
    }
}
