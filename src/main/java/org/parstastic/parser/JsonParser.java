package org.parstastic.parser;

import org.parstastic.node.JsonNode;
import org.parstastic.node.builders.JsonNodeBuilder;
import org.parstastic.parser.exceptions.InvalidJsonException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * This class encapsulates behavior for general <code>JSON</code> parsing.
 */
public class JsonParser implements IJsonParser<JsonNode, InvalidJsonException> {
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
    public JsonParser() {
        super();
    }

    /**
     * Parses a <code>JSON</code> {@link String} fully and returns a parsed {@link JsonNode} object.
     *
     * @param json <code>JSON</code> {@link String} to parse
     * @return parsed {@link JsonNode} object
     * @throws InvalidJsonException when any problem occurs during parsing
     * @see #parseJson(String, AtomicInteger)
     */
    public JsonNode parseJson(final String json) throws InvalidJsonException {
        final AtomicInteger index = new AtomicInteger(0);
        final JsonNode node = parseJson(json, index);

        skipWhitespaces(json, index);
        if (index.get() != json.length()) {
            throw new InvalidJsonException();
        }

        return node;
    }

    /**
     * Parses a <code>JSON</code> {@link String} fully and returns a parsed {@link JsonNode} object.
     *
     * @param json <code>JSON</code> {@link String} to parse
     * @param index the index to start parsing from
     * @return parsed {@link JsonNode} object
     * @throws InvalidJsonException when any problem occurs during parsing
     */
    @Override
    public JsonNode parseJson(final String json, final AtomicInteger index) throws InvalidJsonException {
        skipWhitespaces(json, index);

        final JsonNodeBuilder<?, ?> jsonNodeBuilder = createJsonNodeBuilder(json, index);

        if (jsonNodeBuilder != null) {
            return jsonNodeBuilder.parseJson(json, index);
        } else {
            throw new InvalidJsonException();
        }
    }

    /**
     * Skips all whitespace characters, as defined by {@link #ALLOWED_WHITESPACES}, at the start of the <code>JSON</code> {@link String}.
     *
     * @param json <code>JSON</code> {@link String} to skip whitespaces in
     * @param index the index to start skipping from
     */
    public void skipWhitespaces(final String json, final AtomicInteger index) {
        while (startsWithWhitespace(json, index)) {
            index.getAndIncrement();
        }
    }

    /**
     * Determines whether a <code>JSON</code> {@link String} starts with a whitespace character.
     *
     * @param json <code>JSON</code> {@link String} to check for whitespace
     * @param index the index at which to check for whitespace
     * @return {@code true} if the <code>JSON</code> {@link String} starts with a whitespace character,
     *         {@code false} otherwise
     */
    public boolean startsWithWhitespace(final String json, final AtomicInteger index) {
        return index.get() < json.length() && ALLOWED_WHITESPACES.contains(json.charAt(index.get()));
    }

    /**
     * Creates a {@link JsonNodeBuilder} for a <code>JSON</code> {@link String}.
     *
     * @param json <code>JSON</code> {@link String} to create {@link JsonNodeBuilder} for
     * @param index the index with which to determine the correct {@link JsonNodeBuilder}
     * @return {@link JsonNodeBuilder} object able to parse the given <code>JSON</code> {@link String} or {@code null} if there is none
     */
    private JsonNodeBuilder<?, ?> createJsonNodeBuilder(final String json, final AtomicInteger index) {
        return createJsonNodeBuilders().stream()
                .filter(builder -> builder.canParseJson(json, index))
                .findFirst().orElse(null);
    }

    /**
     * Creates a {@link Set} of an instance each of every {@link JsonNodeBuilder} type currently loaded in the application.
     *
     * @return a {@link Set} of all available {@link JsonNodeBuilder} objects
     */
    @SuppressWarnings("rawtypes")
    private Set<JsonNodeBuilder> createJsonNodeBuilders() {
        final String packageName = JsonNode.class.getPackageName();
        final Reflections reflections = new Reflections(packageName);
        final Set<Class<? extends JsonNodeBuilder>> classes = reflections.getSubTypesOf(JsonNodeBuilder.class);
        return classes.stream()
                .map(this::createJsonNodeBuilder)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Creates an instance of a {@link JsonNodeBuilder} {@link Class} using a public constructor with {@link JsonParser} as parameter.
     * Passes this {@link JsonParser} object as constructor parameter to the {@link JsonNodeBuilder} object.
     *
     * @param clazz {@link Class} object to create a {@link JsonNodeBuilder} of
     * @return a {@link JsonNodeBuilder} object of given {@code clazz} or {@code null} if any exception occurs.
     */
    @SuppressWarnings("rawtypes")
    private JsonNodeBuilder createJsonNodeBuilder(final Class<? extends JsonNodeBuilder> clazz) {
        try {
            return clazz.getConstructor(JsonParser.class).newInstance(this);
        } catch (final NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Checks whether a given <code>JSON</code> {@link String} has a given character at a given index.
     *
     * @param json <code>JSON</code> {@link String} to check character presence in
     * @param index the index to check character presence for
     * @param c the character to check presence for
     * @return {@code true} if the given <code>JSON</code> {@link String} has the given character {@code c} at the given index,
     *         {@code false} if the index is outside the <code>JSON</code> {@link String} length or the character at the index is not {@code c}
     */
    public boolean hasCharAtIndex(final String json, final AtomicInteger index, final char c) {
        final int i = index.get();
        if (i < json.length()) {
            return json.charAt(i) == c;
        }
        return false;
    }
}
