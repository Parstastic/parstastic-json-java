package org.parstastic.jparstastic_json.node.builders;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.parser.IJsonParser;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

/**
 * This abstract class provides generalized methods to parse <code>JSON</code> strings into {@link JsonNode} objects.
 *
 * @param <T> type of the parsed {@link JsonNode} object
 * @param <E> type of {@link InvalidJsonException} that can be thrown during parsing
 */
public abstract class JsonNodeBuilder<T extends JsonNode, E extends InvalidJsonException> implements IJsonParser<T, E> {
    /**
     * Creates a {@link JsonNodeBuilder} object.
     */
    protected JsonNodeBuilder() {
        super();
    }

    /**
     * Determines whether a given <code>JSON</code> {@link String} can be parsed by this {@link JsonNodeBuilder} object.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to parse object from
     * @return {@code true} if this {@link JsonNodeBuilder} can parse the <code>JSON</code> {@link String} into an object,
     *         {@code false} otherwise
     */
    public abstract boolean canParseJson(final JsonParsingProcess parsingProcess);
}
