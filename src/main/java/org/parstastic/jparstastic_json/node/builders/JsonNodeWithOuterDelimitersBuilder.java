package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;

/**
 * This abstract class unifies behavior that is common to other {@link JsonNodeBuilder} classes
 * that build {@link JsonNode} objects which have a delimiter each at the start and at the end.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 */
public abstract class JsonNodeWithOuterDelimitersBuilder<T extends JsonNode, E extends InvalidJsonException> extends JsonNodeBuilder<T, E> {
    /**
     * Character that signals the start of the {@link JsonNode} to build.
     */
    protected final char startDelimiter;
    /**
     * Character that signals the end of the {@link JsonNode} to build.
     */
    protected final char endDelimiter;

    /**
     * Creates a {@link JsonNodeWithOuterDelimitersBuilder} object with given {@link JsonParser}, start delimiter and end delimiter.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     * @param startDelimiter start delimiter of the parsed {@link JsonNode}
     * @param endDelimiter end delimiter of the parsed {@link JsonNode}
     */
    protected JsonNodeWithOuterDelimitersBuilder(final JsonParser jsonParser,
                                                 final char startDelimiter,
                                                 final char endDelimiter) {
        super(jsonParser);
        this.startDelimiter = startDelimiter;
        this.endDelimiter = endDelimiter;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This {@link JsonNodeWithInnerDelimitersBuilder} can parse a <code>JSON</code> {@link String}
     * if the character at starting index is {@link #startDelimiter}.
     * </p>
     */
    @Override
    public boolean canParseJson(final String json, final AtomicInteger index) {
        return isAtStartDelimiter(json, index);
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} is at the start delimiter required for starting the parsing process.
     *
     * @param json <code>JSON</code> {@link String} to check for start delimiter.
     * @param index the index for which to check start delimiter presence
     * @return {@code true} if the character at {@code index} in the given <code>JSON</code> {@link String} is {@link #startDelimiter},
     *         {@code false} otherwise
     * @see #startDelimiter
     */
    protected boolean isAtStartDelimiter(final String json, final AtomicInteger index) {
        return this.jsonParser.hasCharAtIndex(json, index, this.startDelimiter);
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} is at the end delimiter required for ending the parsing process.
     *
     * @param json <code>JSON</code> {@link String} to check for end delimiter
     * @param index the index for which to check end delimiter presence
     * @return {@code true} if the character at {@code index} in the given <code>JSON</code> {@link String} is {@link #endDelimiter},
     *         {@code false} otherwise
     * @see #endDelimiter
     */
    protected boolean isAtEndDelimiter(final String json, final AtomicInteger index) {
        return this.jsonParser.hasCharAtIndex(json, index, this.endDelimiter);
    }

    @Override
    public T parseJson(final String json, final AtomicInteger index) throws E {
        validateStartDelimiter(json, index);

        iterateChars(json, index);

        validateEndDelimiter(json, index);

        return createNode();
    }

    /**
     * Validates if a <code>JSON</code> {@link String} has {@link #startDelimiter} at {@code index} or throws an exception otherwise.
     *
     * @param json <code>JSON</code> {@link String} to validate {@link #startDelimiter} for
     * @param index the index at which {@link #startDelimiter} has to be
     * @throws E if {@link #startDelimiter} is not at {@code index} of <code>JSON</code> {@link String}
     * @see #isAtStartDelimiter(String, AtomicInteger)
     */
    protected void validateStartDelimiter(final String json, final AtomicInteger index) throws E {
        validateDelimiter(json, index, this::isAtStartDelimiter);
    }

    /**
     * Validates if a <code>JSON</code> {@link String} has {@link #endDelimiter} at {@code index} or throws an exception otherwise.
     *
     * @param json <code>JSON</code> {@link String} to validate {@link #endDelimiter} for
     * @param index the index at which {@link #endDelimiter} has to be
     * @throws E if {@link #endDelimiter} is not at {@code index} of <code>JSON</code> {@link String}
     * @see #isAtEndDelimiter(String, AtomicInteger)
     */
    protected void validateEndDelimiter(final String json, final AtomicInteger index) throws E {
        validateDelimiter(json, index, this::isAtEndDelimiter);
    }

    /**
     * Validates if a delimiter is at {@code index} of <code>JSON</code> {@link String} using a {@code validationFunction}.
     * If that {@code validationFunction} returns {@code false}, an exception is thrown.
     * Otherwise, the {@code index} is increased by one.
     *
     * @param json <code>JSON</code> {@link String} to validate a delimiter for
     * @param index the index at which a delimiter has to be
     * @param validationFunction {@link BiPredicate} to validate delimiter presence with
     * @throws E when the delimiter is not at {@code index} in <code>JSON</code> {@link String}
     */
    protected void validateDelimiter(final String json,
                                     final AtomicInteger index,
                                     final BiPredicate<String, AtomicInteger> validationFunction) throws E {
        if (!validationFunction.test(json, index)) {
            throw createException();
        }
        index.incrementAndGet();
    }

    /**
     * Iterates over all remaining characters in <code>JSON</code> {@link String} or until the {@link #endDelimiter} is reached.
     *
     * @param json <code>JSON</code> {@link String} to iterate over
     * @param index the index to start the iteration from
     * @throws E when any problem occurs during iterating over, reading or processing characters
     */
    protected void iterateChars(final String json, final AtomicInteger index) throws E {
        while (index.get() < json.length()) {
            if (!processChar(json, index)) {
                return;
            }
        }
    }

    /**
     * Represents one iteration of {@link #iterateChars(String, AtomicInteger)}.
     * Determines whether the iteration should be continued or not.
     * Processes the character at {@code index} in <code>JSON</code> {@link String} and increments the {@code index}.
     * 
     * @param json <code>JSON</code> {@link String} that is parsed
     * @param index the index of the character to process
     * @return {@code true} if the iteration should continue,
     *         {@code false} otherwise
     * @throws E when any problem occurs during processing of character
     */
    protected boolean processChar(final String json, final AtomicInteger index) throws E {
        final char c = json.charAt(index.get());

        if (!processChar(json, index, c)) {
            return false;
        }

        index.incrementAndGet();

        return true;
    }

    /**
     * Processes the character at {@code index} in <code>JSON</code> {@link String}.
     *
     * @param json <code>JSON</code> {@link String} that is parsed
     * @param index the index of the character to process
     * @param c the character to process
     * @return {@code true} if the iteration should continue,
     *         {@code false} otherwise
     * @throws E when any problem occurs during processing of character
     */
    protected abstract boolean processChar(final String json, final AtomicInteger index, final char c) throws E;

    /**
     * Creates a {@link JsonNode} object with previously specified parameters.
     *
     * @return a {@link JsonNode} object with previously specified parameters
     */
    protected abstract T createNode();

    /**
     * Creates an {@link InvalidJsonException} object corresponding to the {@link JsonNode} type to create.
     *
     * @return an {@link InvalidJsonException} object
     */
    protected abstract E createException();
}
