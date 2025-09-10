package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.function.Predicate;

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
    public boolean canParseJson(final JsonParsingProcess parsingProcess) {
        return isAtStartDelimiter(parsingProcess);
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} is at the start delimiter required for starting the parsing process.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to check for start delimiter
     * @return {@code true} if the character at {@code index} in the given <code>JSON</code> {@link String} is {@link #startDelimiter},
     *         {@code false} otherwise
     * @see #startDelimiter
     */
    protected boolean isAtStartDelimiter(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isAtChar(this.startDelimiter);
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} is at the end delimiter required for ending the parsing process.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to check for end delimiter
     * @return {@code true} if the character at {@code index} in the given <code>JSON</code> {@link String} is {@link #endDelimiter},
     *         {@code false} otherwise
     * @see #endDelimiter
     */
    protected boolean isAtEndDelimiter(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isAtChar(this.endDelimiter);
    }

    @Override
    public T parseJson(final JsonParsingProcess parsingProcess) throws E {
        validateStartDelimiter(parsingProcess);

        iterateChars(parsingProcess);

        validateEndDelimiter(parsingProcess);

        return createNode();
    }

    /**
     * Validates if a <code>JSON</code> {@link String} has {@link #startDelimiter} at {@code index} or throws an exception otherwise.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to validate {@link #startDelimiter} for
     * @throws E if {@link #startDelimiter} is not at {@code index} of <code>JSON</code> {@link String}
     * @see #isAtStartDelimiter(JsonParsingProcess)
     */
    protected void validateStartDelimiter(final JsonParsingProcess parsingProcess) throws E {
        validateDelimiter(parsingProcess, this::isAtStartDelimiter);
    }

    /**
     * Validates if a <code>JSON</code> {@link String} has {@link #endDelimiter} at {@code index} or throws an exception otherwise.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to validate {@link #endDelimiter} for
     * @throws E if {@link #endDelimiter} is not at {@code index} of <code>JSON</code> {@link String}
     * @see #isAtEndDelimiter(JsonParsingProcess)
     */
    protected void validateEndDelimiter(final JsonParsingProcess parsingProcess) throws E {
        validateDelimiter(parsingProcess, this::isAtEndDelimiter);
    }

    /**
     * Validates if a delimiter is at {@code index} of <code>JSON</code> {@link String} using a {@code validationFunction}.
     * If that {@code validationFunction} returns {@code false}, an exception is thrown.
     * Otherwise, the {@code index} is increased by one.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to validate a delimiter for
     * @param validationFunction {@link Predicate} to validate delimiter presence with
     * @throws E when the delimiter is not at {@code index} in <code>JSON</code> {@link String}
     */
    protected void validateDelimiter(final JsonParsingProcess parsingProcess,
                                     final Predicate<JsonParsingProcess> validationFunction) throws E {
        if (!validationFunction.test(parsingProcess)) {
            throw createException();
        }
        parsingProcess.incrementIndex();
    }

    /**
     * Iterates over all remaining characters in <code>JSON</code> {@link String} or until the {@link #endDelimiter} is reached.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to iterate over
     * @throws E when any problem occurs during iterating over, reading or processing characters
     */
    protected void iterateChars(final JsonParsingProcess parsingProcess) throws E {
        while (parsingProcess.isIndexInJson()) {
            if (!processChar(parsingProcess)) {
                return;
            }
        }
    }

    /**
     * Represents one iteration of {@link #iterateChars(JsonParsingProcess)}.
     * Determines whether the iteration should be continued or not.
     * Processes the character at {@code index} in <code>JSON</code> {@link String} and increments the {@code index}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process
     * @return {@code true} if the iteration should continue,
     *         {@code false} otherwise
     * @throws E when any problem occurs during processing of character
     */
    protected boolean processChar(final JsonParsingProcess parsingProcess) throws E {
        final char c = parsingProcess.getChar();

        if (!processChar(parsingProcess, c)) {
            return false;
        }

        parsingProcess.incrementIndex();

        return true;
    }

    /**
     * Processes the character at {@code index} in <code>JSON</code> {@link String}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process
     * @param c the character to process
     * @return {@code true} if the iteration should continue,
     *         {@code false} otherwise
     * @throws E when any problem occurs during processing of character
     */
    protected abstract boolean processChar(final JsonParsingProcess parsingProcess, final char c) throws E;

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
