package org.parstastic.jparstastic_json.node.string;

import org.parstastic.jparstastic_json.node.builders.JsonNodeWithOuterDelimitersBuilder;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonStringNodeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * This class is responsible for parsing <code>JSON</code> strings into {@link StringNode} objects.
 */
public class StringNodeBuilder extends JsonNodeWithOuterDelimitersBuilder<StringNode, InvalidJsonStringNodeException> {
    /**
     * Character used for escaping formatting symbols specified in {@link #ESCAPE_TARGETS_WITH_VALIDATION_FUNCTION}.
     */
    public static final char ESCAPE_CHARACTER = '\\';

    /**
     * Function used to validate a single escaped character.
     */
    private static final BiPredicate<String, AtomicInteger> SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION = (json, index) -> true;

    /**
     * Function to validate an escaped unicode sequence.
     */
    private static final BiPredicate<String, AtomicInteger> UNICODE_VALIDATION_FUNCTION = (json, index)  -> {
        for (int i = 0; i < 4; i++) {
            final int newIndex = index.incrementAndGet();
            if (newIndex >= json.length() || Character.digit(json.charAt(newIndex), 16) == -1) {
                return false;
            }
        }
        return true;
    };

    /**
     * All targets that can be escaped by {@link #ESCAPE_CHARACTER} and a function per target to validate it.
     */
    public static final Map<Character, BiPredicate<String, AtomicInteger>> ESCAPE_TARGETS_WITH_VALIDATION_FUNCTION = Map.of(
            '"', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            '\\', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            '/', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            'b', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            'f', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            'n', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            'r', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            't', SINGLE_ESCAPE_TARGET_VALIDATION_FUNCTION,
            'u', UNICODE_VALIDATION_FUNCTION
    );

    /**
     * Characters that will be part of the {@link StringNode}.
     */
    private final List<Character> chars;

    /**
     * Stores whether the char sequence of the <code>JSON</code> {@link String} is currently escaped.
     */
    private boolean isEscaped;

    /**
     * Creates a {@link StringNodeBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    public StringNodeBuilder(final JsonParser jsonParser) {
        super(jsonParser, StringNode.DELIMITER, StringNode.DELIMITER);
        this.chars = new LinkedList<>();
        this.isEscaped = false;
    }

    /**
     * {@inheritDoc}
     *
     * The character may not be escaped.
     *
     * @return {@code true} if the character at {@code index} in the given <code>JSON</code> {@link String} is not escaped and is {@link #endDelimiter},
     *         {@code false} otherwise
     */
    @Override
    protected boolean isAtEndDelimiter(final String json, final AtomicInteger index) {
        return !this.isEscaped && super.isAtEndDelimiter(json, index);
    }

    /**
     * Checks whether the given character is valid for a {@link StringNode} or if an escaped sequence is valid.
     * Valid characters and escaped sequences will be added to {@link #chars}.
     * Throws an exception on discovery of invalid escaped sequence.
     * Also checks if the {@link #endDelimiter} has been reached.
     *
     * @throws InvalidJsonStringNodeException when any invalid character is found
     */
    @Override
    protected boolean processChar(final String json, final AtomicInteger index, final char c) throws InvalidJsonStringNodeException {
        if (!this.isEscaped) {
            if (c == ESCAPE_CHARACTER) {
                this.isEscaped = true;
            } else if (c == this.endDelimiter) {
                return false;
            } else {
                this.chars.add(c);
            }
        } else {
            this.isEscaped = false;
            if (isValidEscapeTarget(json, index, c)) {
                this.chars.add(c);
            } else {
                throw createException();
            }
        }

        return true;
    }

    /**
     * Checks whether a character is a valid target for escaping with {@link #ESCAPE_CHARACTER}.
     *
     * @param json <code>JSON</code> {@link String} in which the character is escaped
     * @param index the index of the escaped character
     * @param c the escaped character
     * @return {@code true} if the escaped character is a valid target,
     *         {@code false} otherwise
     */
    private boolean isValidEscapeTarget(final String json, final AtomicInteger index, final char c) {
        return ESCAPE_TARGETS_WITH_VALIDATION_FUNCTION.getOrDefault(c, (j, i) -> false).test(json, index);
    }

    /**
     * Creates a {@link StringNode} object after combining {@link #chars} into a unified {@link String} text.
     *
     * @return a {@link StringNode} object with {@link #chars} as text
     */
    @Override
    protected StringNode createNode() {
        final String value = this.chars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        return new StringNode(value);
    }

    /**
     * Creates an {@link InvalidJsonStringNodeException}
     *
     * @return an {@link InvalidJsonStringNodeException}
     */
    @Override
    protected InvalidJsonStringNodeException createException() {
        return new InvalidJsonStringNodeException();
    }
}
