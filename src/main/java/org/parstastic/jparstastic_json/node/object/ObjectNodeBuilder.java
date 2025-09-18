package org.parstastic.jparstastic_json.node.object;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.builders.JsonNodeWithInnerDelimitersBuilder;
import org.parstastic.jparstastic_json.node.string.StringNodeBuilder;
import org.parstastic.jparstastic_json.parser.FullStringJsonParser;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonObjectNodeException;

import static org.parstastic.jparstastic_json.node.object.ObjectNode.ObjectNodeProperty;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link ObjectNode} objects.
 */
public class ObjectNodeBuilder extends JsonNodeWithInnerDelimitersBuilder<ObjectNode, InvalidJsonObjectNodeException, ObjectNodeProperty> {
    /**
     * This record is responsible for parsing <code>JSON</code> strings into {@link ObjectNodeProperty} objects.
     */
    private static class ObjectNodePropertyBuilder extends JsonParser<ObjectNodeProperty, InvalidJsonException> {
        /**
         * Creates an {@link ObjectNodePropertyBuilder} object.
         */
        private ObjectNodePropertyBuilder() {
            super();
        }

        @Override
        public ObjectNodeProperty parseJson(final JsonParsingProcess parsingProcess) throws InvalidJsonException {
            final String key = parseKey(parsingProcess);

            validateDelimiter(parsingProcess);

            final JsonNode value = parseValue(parsingProcess);

            return new ObjectNodeProperty(key, value);
        }

        /**
         * Parses a <code>JSON</code> {@link String} into the key of an {@link ObjectNodeProperty}.
         * Skips whitespaces before and after.
         *
         * @param parsingProcess a <code>JSON</code> {@link String} parsing process
         * @return {@link String} which is the key
         * @throws InvalidJsonException when any problem occurs during parsing
         */
        private String parseKey(final JsonParsingProcess parsingProcess) throws InvalidJsonException {
            skipWhitespaces(parsingProcess);
            final String key = new StringNodeBuilder().parseJson(parsingProcess).getValue();
            skipWhitespaces(parsingProcess);

            return key;
        }

        /**
         * Validates the {@link ObjectNodeProperty#DELIMITER} at the given index of <code>JSON</code> {@link String}.
         * After successful validation, increments {@code index}.
         *
         * @param parsingProcess a <code>JSON</code> {@link String} parsing process
         * @throws InvalidJsonException when the delimiter is not found
         */
        private void validateDelimiter(final JsonParsingProcess parsingProcess) throws InvalidJsonException {
            if (parsingProcess.isAtChar(ObjectNodeProperty.DELIMITER)) {
                parsingProcess.incrementIndex();
            } else {
                throw new InvalidJsonException();
            }
        }

        /**
         * Parses a <code>JSON</code> {@link String} into the value of an {@link ObjectNodeProperty}.
         * Skips whitespaces before and after.
         *
         * @param parsingProcess a <code>JSON</code> {@link String} parsing process
         * @return {@link JsonNode} which is the value
         * @throws InvalidJsonException when any problem occurs during parsing
         */
        private JsonNode parseValue(final JsonParsingProcess parsingProcess) throws InvalidJsonException {
            skipWhitespaces(parsingProcess);
            final JsonNode value = new FullStringJsonParser().parseJson(parsingProcess);
            skipWhitespaces(parsingProcess);

            return value;
        }

        @Override
        protected InvalidJsonException createException(final InvalidJsonException exception) {
            return exception;
        }
    }

    /**
     * Creates an {@link ObjectNodeBuilder} object.
     */
    public ObjectNodeBuilder() {
        super(ObjectNode.DELIMITER_START, ObjectNode.DELIMITER_END, ObjectNode.DELIMITER_ELEMENTS, new ObjectNodePropertyBuilder());
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

    @Override
    protected InvalidJsonObjectNodeException createException(final InvalidJsonException exception) {
        return new InvalidJsonObjectNodeException(exception);
    }
}
