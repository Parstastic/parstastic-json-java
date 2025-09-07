package org.parstastic.node.object;

import org.parstastic.node.JsonNode;
import org.parstastic.node.builders.JsonNodeWithInnerDelimitersBuilder;
import org.parstastic.node.string.StringNodeBuilder;
import org.parstastic.parser.IJsonParser;
import org.parstastic.parser.JsonParser;
import org.parstastic.parser.exceptions.InvalidJsonException;
import org.parstastic.parser.exceptions.InvalidJsonObjectNodeException;

import java.util.concurrent.atomic.AtomicInteger;

import static org.parstastic.node.object.ObjectNode.ObjectNodeProperty;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link ObjectNode} objects.
 */
public class ObjectNodeBuilder extends JsonNodeWithInnerDelimitersBuilder<ObjectNode, InvalidJsonObjectNodeException, ObjectNodeProperty> {
    /**
     * This record is responsible for parsing <code>JSON</code> strings into {@link ObjectNodeProperty} objects.
     * Creates an {@link ObjectNodePropertyBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    private record ObjectNodePropertyBuilder(JsonParser jsonParser) implements IJsonParser<ObjectNodeProperty, InvalidJsonException> {
        @Override
        public ObjectNodeProperty parseJson(final String json, final AtomicInteger index) throws InvalidJsonException {
            final String key = parseKey(json, index);

            validateDelimiter(json, index);

            final JsonNode value = parseValue(json, index);

            return new ObjectNodeProperty(key, value);
        }

        /**
         * Parses a <code>JSON</code> {@link String} into the key of an {@link ObjectNodeProperty}.
         * Skips whitespaces before and after.
         *
         * @param json <code>JSON</code> {@link String} to parse
         * @param index the index at which to start parsing
         * @return {@link String} which is the key
         * @throws InvalidJsonException when any problem occurs during parsing
         */
        private String parseKey(final String json, final AtomicInteger index) throws InvalidJsonException {
            this.jsonParser.skipWhitespaces(json, index);
            final String key = new StringNodeBuilder(this.jsonParser).parseJson(json, index).getValue();
            this.jsonParser.skipWhitespaces(json, index);

            return key;
        }

        /**
         * Validates the {@link ObjectNodeProperty#DELIMITER} at the given index of <code>JSON</code> {@link String}.
         * After successful validation, increments {@code index}.
         *
         * @param json <code>JSON</code> {@link String} to validate
         * @param index the index at which the delimiter should be
         * @throws InvalidJsonException when the delimiter is not found
         */
        private void validateDelimiter(final String json, final AtomicInteger index) throws InvalidJsonException {
            if (this.jsonParser.hasCharAtIndex(json, index, ObjectNodeProperty.DELIMITER)) {
                index.incrementAndGet();
            } else {
                throw new InvalidJsonException();
            }
        }

        /**
         * Parses a <code>JSON</code> {@link String} into the value of an {@link ObjectNodeProperty}.
         * Skips whitespaces before and after.
         *
         * @param json <code>JSON</code> {@link String} to parse
         * @param index the index at which to start parsing
         * @return {@link JsonNode} which is the value
         * @throws InvalidJsonException when any problem occurs during parsing
         */
        private JsonNode parseValue(final String json, final AtomicInteger index) throws InvalidJsonException {
            this.jsonParser.skipWhitespaces(json, index);
            final JsonNode value = this.jsonParser.parseJson(json, index);
            this.jsonParser.skipWhitespaces(json, index);

            return value;
        }
    }

    /**
     * Creates an {@link ObjectNodeBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    public ObjectNodeBuilder(final JsonParser jsonParser) {
        super(jsonParser, ObjectNode.DELIMITER_START, ObjectNode.DELIMITER_END, ObjectNode.DELIMITER_ELEMENTS, new ObjectNodePropertyBuilder(jsonParser));
    }

    /**
     * Creates an {@link ObjectNode} object with parsed {@link #elements}.
     *
     * @return an {@link ObjectNode} object with parsed {@link #elements}
     */
    @Override
    protected ObjectNode createNode() {
        return new ObjectNode(this.elements);
    }

    /**
     * Creates an {@link InvalidJsonObjectNodeException}.
     *
     * @return an {@link InvalidJsonObjectNodeException}
     */
    @Override
    protected InvalidJsonObjectNodeException createException() {
        return new InvalidJsonObjectNodeException();
    }
}
