package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.parser.IJsonParser;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.LinkedList;
import java.util.List;

/**
 * This abstract class unifies behavior that is common to other {@link JsonNodeBuilder} classes
 * that build {@link JsonNode} objects which have an inner delimiter between elements.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 * @param <P> type of the elements
 */
public abstract class JsonNodeWithInnerDelimitersBuilder<T extends JsonNode, E extends InvalidJsonException, P extends JsonParticle> extends JsonNodeWithOuterDelimitersBuilder<T, E> {
    /**
     * Character that signals the next element.
     */
    protected final char elementDelimiter;
    /**
     * {@link IJsonParser} used to parse the elements.
     */
    protected final IJsonParser<P, ?> elementParser;
    /**
     * The elements of the parsed {@link JsonNode}.
     */
    protected final List<P> elements;

    /**
     * Creates a {@link JsonNodeWithInnerDelimitersBuilder} object with given start delimiter, end delimiter, element delimiter and element parser.
     *
     * @param startDelimiter start delimiter of the parsed {@link JsonNode}
     * @param endDelimiter end delimiter of the parsed {@link JsonNode}
     * @param elementDelimiter delimiter for the elements of the parsed {@link JsonNode}
     * @param elementParser {@link IJsonParser} that can be invoked for element parsing
     */
    protected JsonNodeWithInnerDelimitersBuilder(final char startDelimiter,
                                                 final char endDelimiter,
                                                 final char elementDelimiter,
                                                 final IJsonParser<P, ?> elementParser) {
        super(startDelimiter, endDelimiter);
        this.elementDelimiter = elementDelimiter;
        this.elementParser = elementParser;
        this.elements = new LinkedList<>();
    }

    /**
     * {@inheritDoc}
     *
     * Before processing, skips whitespaces at the start of <code>JSON</code> {@link String}.
     *
     * @throws E when any problem occurs during processing of character
     */
    @Override
    protected boolean processChar(final JsonParsingProcess parsingProcess) throws E {
        skipWhitespaces(parsingProcess);

        return super.processChar(parsingProcess);
    }

    /**
     * {@inheritDoc}
     *
     * Does so by validating that it is not the {@link #endDelimiter} and then parsing the current element.
     * After parsing, skips whitespaces.
     *
     * @throws E when any problem occurs during processing of character
     */
    @Override
    protected boolean processChar(final JsonParsingProcess parsingProcess, final char c) throws E {
        if (isAtEndDelimiter(parsingProcess)) {
            return false;
        }

        final P element = this.elementParser.parseJson(parsingProcess);
        this.elements.add(element);

        skipWhitespaces(parsingProcess);
        return !isAtEndDelimiter(parsingProcess);
    }
}
