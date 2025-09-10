package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.Optional;
import java.util.Set;

/**
 * This abstract class unifies behavior that is common to other {@link JsonNodeBuilder} classes
 * that build {@link JsonNode} objects which always match a full text.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 * @param <O> type of the matched text
 */
public abstract class JsonNodeFullTextMatchingBuilder<T extends JsonNode, E extends InvalidJsonException, O> extends JsonNodeBuilder<T, E> {
    /**
     * {@link Set} of possible values the {@link JsonNode} can consist of.
     */
    private final Set<O> possibleValues;

    /**
     * Creates a {@link JsonNodeFullTextMatchingBuilder} object with given {@link Set} of possible values.
     *
     * @param possibleValues {@link Set} of possible values
     */
    protected JsonNodeFullTextMatchingBuilder(final Set<O> possibleValues) {
        super();
        this.possibleValues = possibleValues;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * This {@link JsonNodeFullTextMatchingBuilder} can parse a <code>JSON</code> {@link String}
     * if it starts with any of {@link #possibleValues}.
     * </p>
     */
    @Override
    public boolean canParseJson(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isIndexInJson() && getMatchingValue(parsingProcess).isPresent();
    }

    /**
     * Returns an {@link Optional} of the element of {@link #possibleValues} with which the <code>JSON</code> {@link String} starts.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to get matching value for
     * @return an {@link Optional} of the matching element of {@link #possibleValues} if there is one,
     *         an empty {@link Optional} otherwise
     */
    protected Optional<O> getMatchingValue(final JsonParsingProcess parsingProcess) {
        return this.possibleValues.stream()
                .filter(fullTextObject -> parsingProcess.startsWith(fullTextObject.toString()))
                .findFirst();
    }

    @Override
    public T parseJson(final JsonParsingProcess parsingProcess) throws E {
        final Optional<O> valueOptional = getMatchingValue(parsingProcess);
        if (valueOptional.isEmpty()) {
            throw createException();
        }

        final O value = valueOptional.get();
        for (int i = 0; i < value.toString().length(); i++) {
            parsingProcess.incrementIndex();
        }
        return createNode(value);
    }

    /**
     * Creates a {@link JsonNode} object with given {@code value}.
     *
     * @param value the value to create the {@link JsonNode} object with
     * @return a {@link JsonNode} object with given {@code value}
     */
    protected abstract T createNode(final O value);

    /**
     * Creates an {@link InvalidJsonException} object corresponding to the {@link JsonNode} type to create.
     *
     * @return an {@link InvalidJsonException} object
     */
    protected abstract E createException();
}
