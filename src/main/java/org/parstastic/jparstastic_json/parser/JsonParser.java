package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.Set;

/**
 * This abstract class represents anything that can parse a <code>JSON</code> {@link String} partially or fully.
 *
 * @param <T> type of the returned {@link JsonParticle}
 * @param <E> type of {@link InvalidJsonException} that can be thrown
 */
public abstract class JsonParser<T extends JsonParticle, E extends InvalidJsonException> {
    /**
     * These are all whitespace characters that are allowed within <code>JSON</code>.
     * This includes:
     * <ul>
     *     <li>
     *         <b>space</b>
     *     </li>
     *     <li>
     *         <b>horizontal tab</b>
     *     </li>
     *     <li>
     *         <b>line feed</b>
     *     </li>
     *     <li>
     *         <b>carriage return</b>
     *     </li>
     * </ul>
     */
    public static final Set<Character> ALLOWED_WHITESPACES = Set.of(' ', '\t', '\n', '\r');

    /**
     * Creates a {@link JsonParser} object.
     */
    protected JsonParser() {
        super();
    }

    /**
     * Parses a <code>JSON</code> {@link String} partially or fully and returns a parsed object.
     *
     * @param json <code>JSON</code> {@link String} to parse
     * @return parsed {@link JsonParticle} object of type {@code T}
     * @throws E when any problem occurs during parsing
     * @see #parseJson(JsonParsingProcess)
     */
    public T parseJson(final String json) throws E {
        try {
            return parseJson(createParsingProcess(json));
        } catch (final InvalidJsonException e) {
            final E exception = createException(e);
            if (exception.getClass() == e.getClass()) {
                throw e;
            } else {
                throw exception;
            }
        }
    }

    /**
     * Parses a <code>JSON</code> {@link String} partially or fully and returns a parsed object.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process
     * @return parsed {@link JsonParticle} object of type {@code T}
     * @throws E when any problem occurs during parsing
     * @see #parseJson(String)
     */
    public abstract T parseJson(final JsonParsingProcess parsingProcess) throws E;

    /**
     * Creates a {@link JsonParsingProcess} from a <code>JSON</code> {@link String}.
     *
     * @param json <code>JSON</code> {@link String} to create {@link JsonParsingProcess} with
     * @return a {@link JsonParsingProcess} for <code>JSON</code> {@link String}
     */
    protected JsonParsingProcess createParsingProcess(final String json) {
        return new JsonParsingProcess(json);
    }

    /**
     * Skips all whitespace characters, as defined by {@link #ALLOWED_WHITESPACES}, at the start of the <code>JSON</code> {@link String}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to skip whitespaces in
     */
    protected void skipWhitespaces(final JsonParsingProcess parsingProcess) {
        while (startsWithWhitespace(parsingProcess)) {
            parsingProcess.incrementIndex();
        }
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} starts with a whitespace character.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to check for whitespace
     * @return {@code true} if the <code>JSON</code> {@link String} starts with a whitespace character,
     *         {@code false} otherwise
     */
    protected boolean startsWithWhitespace(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isCharValid(ALLOWED_WHITESPACES::contains);
    }

    /**
     * Creates an {@link InvalidJsonException} object corresponding to the {@link JsonParticle} type to create
     * using a given {@link InvalidJsonException} as cause.
     *
     * @param exception cause of the {@link InvalidJsonException}
     * @return an {@link InvalidJsonException} object
     */
    protected abstract E createException(final InvalidJsonException exception);
}
