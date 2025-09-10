package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

/**
 * This interface represents anything that can parse a <code>JSON</code> {@link String} partially or fully.
 *
 * @param <T> type of the returned {@link JsonParticle}
 * @param <E> type of {@link InvalidJsonException} that can be thrown
 */
public interface IJsonParser<T extends JsonParticle, E extends InvalidJsonException> {
    /**
     * Parses a <code>JSON</code> {@link String} partially or fully and returns a parsed object.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process
     * @return parsed {@link JsonParticle} object of type {@code T}
     * @throws E when any problem occurs during parsing
     */
    T parseJson(final JsonParsingProcess parsingProcess) throws E;
}
