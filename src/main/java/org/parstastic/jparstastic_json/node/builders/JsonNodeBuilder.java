package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.IJsonParser;
import org.parstastic.jparstastic_json.parser.JsonParser;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This abstract class provides generalized methods to parse <code>JSON</code> strings into {@link JsonNode} objects.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 */
public abstract class JsonNodeBuilder<T extends JsonNode, E extends InvalidJsonException> implements IJsonParser<T, E> {
    protected final JsonParser jsonParser;

    /**
     * Creates a {@link JsonNodeBuilder} object with given {@link JsonParser}.
     *
     * @param jsonParser {@link JsonParser} that can be invoked for parsing
     */
    protected JsonNodeBuilder(final JsonParser jsonParser) {
        super();
        this.jsonParser = jsonParser;
    }

    /**
     * Determines whether a given <code>JSON</code> {@link String} can be parsed by this {@link JsonNodeBuilder} object.
     *
     * @param json <code>JSON</code> {@link String} to parse object from
     * @param index index in the <code>JSON</code> {@link String} to start parsing from
     * @return {@code true} if this {@link JsonNodeBuilder} can parse the <code>JSON</code> {@link String} into an object,
     *         {@code false} otherwise
     */
    public abstract boolean canParseJson(final String json, final AtomicInteger index);
}
