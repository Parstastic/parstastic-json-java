package org.parstastic.parser;

import org.parstastic.node.IJsonParticle;
import org.parstastic.parser.exceptions.InvalidJsonException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This interface represents anything that can parse a <code>JSON</code> {@link String} partially or fully.
 *
 * @param <T> type of the returned {@link IJsonParticle}
 * @param <E> type of {@link InvalidJsonException} that can be thrown
 */
public interface IJsonParser<T extends IJsonParticle, E extends InvalidJsonException> {
    /**
     * Parses a <code>JSON</code> {@link String} partially or fully and returns a parsed object.
     * The {@code index} may be modified.
     *
     * @param json <code>JSON</code> {@link String} to parse
     * @param index the index to start parsing from
     * @return parsed {@link IJsonParticle} object of type {@code T}
     * @throws E when any problem occurs during parsing
     */
    T parseJson(final String json, final AtomicInteger index) throws E;
}
