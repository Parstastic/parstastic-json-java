package org.parstastic.jparstastic_json.node.object;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.StringifyOptions;
import org.parstastic.jparstastic_json.node.string.StringNode;

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
        public String stringify(final StringifyOptions options) {
            return options.getIndentation() + DELIMITER_KEY + key + DELIMITER_KEY + DELIMITER + value.toString();
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
    public String stringify(final StringifyOptions options) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(options.getIndentation()).append(DELIMITER_START).append(options.getLineBreak());
        for (int i = 0; i < this.properties.size(); i++) {
            stringBuilder.append(this.properties.get(i).stringify(options.withIncreasedIndentationLevel()));
            if (i < this.properties.size() - 1) {
                stringBuilder.append(DELIMITER_ELEMENTS);
            }
            stringBuilder.append(options.getLineBreak());
        }
        stringBuilder.append(options.getIndentation()).append(DELIMITER_END);
        return stringBuilder.toString();
    }
}
