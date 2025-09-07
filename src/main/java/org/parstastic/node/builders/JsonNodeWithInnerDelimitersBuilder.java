package org.parstastic.node.builders;

import org.parstastic.node.IJsonParticle;
import org.parstastic.node.JsonNode;
import org.parstastic.parser.IJsonParser;
import org.parstastic.parser.JsonParser;
import org.parstastic.parser.exceptions.InvalidJsonException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This abstract class unifies behavior that is common to other {@link JsonNodeBuilder} classes
 * that build {@link JsonNode} objects which have an inner delimiter between elements.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 * @param <P> type of the elements
 */
public abstract class JsonNodeWithInnerDelimitersBuilder<T extends JsonNode, E extends InvalidJsonException, P extends IJsonParticle> extends JsonNodeWithOuterDelimitersBuilder<T, E> {
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
     * Creates a {@link JsonNodeWithInnerDelimitersBuilder} object with given {@link JsonParser}, start delimiter, end delimiter, element delimiter and element parser.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     * @param startDelimiter start delimiter of the parsed {@link JsonNode}
     * @param endDelimiter end delimiter of the parsed {@link JsonNode}
     * @param elementDelimiter delimiter for the elements of the parsed {@link JsonNode}
     * @param elementParser {@link IJsonParser} that can be invoked for element parsing
     */
    protected JsonNodeWithInnerDelimitersBuilder(final JsonParser jsonParser,
                                                 final char startDelimiter,
                                                 final char endDelimiter,
                                                 final char elementDelimiter,
                                                 final IJsonParser<P, ?> elementParser) {
        super(jsonParser, startDelimiter, endDelimiter);
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
    protected boolean processChar(final String json, final AtomicInteger index) throws E {
        this.jsonParser.skipWhitespaces(json, index);

        return super.processChar(json, index);
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
    protected boolean processChar(final String json, final AtomicInteger index, final char c) throws E {
        if (isAtEndDelimiter(json, index)) {
            return false;
        }

        final P element = this.elementParser.parseJson(json, index);
        this.elements.add(element);

        this.jsonParser.skipWhitespaces(json, index);
        return !isAtEndDelimiter(json, index);
    }
}
