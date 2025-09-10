package org.parstastic.jparstastic_json.parser;

import org.parstastic.jparstastic_json.node.JsonNode;
import org.parstastic.jparstastic_json.node.builders.JsonNodeBuilder;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;
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
     * @see #parseJson(JsonParsingProcess)
     */
    public JsonNode parseJson(final String json) throws InvalidJsonException {
        final JsonParsingProcess parsingProcess = new JsonParsingProcess(json);
        final JsonNode node = parseJson(parsingProcess);

        skipWhitespaces(parsingProcess);
        if (!parsingProcess.isFinished()) {
            throw new InvalidJsonException();
        }

        return node;
    }

    /**
     * Parses a <code>JSON</code> {@link String} fully and returns a parsed {@link JsonNode} object.
     *
     * @return parsed {@link JsonNode} object
     * @throws InvalidJsonException when any problem occurs during parsing
     */
    @Override
    public JsonNode parseJson(final JsonParsingProcess parsingProcess) throws InvalidJsonException {
        skipWhitespaces(parsingProcess);

        final JsonNodeBuilder<?, ?> jsonNodeBuilder = createJsonNodeBuilder(parsingProcess);

        if (jsonNodeBuilder != null) {
            return jsonNodeBuilder.parseJson(parsingProcess);
        } else {
            throw new InvalidJsonException();
        }
    }

    /**
     * Skips all whitespace characters, as defined by {@link #ALLOWED_WHITESPACES}, at the start of the <code>JSON</code> {@link String}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to skip whitespaces in
     */
    public void skipWhitespaces(final JsonParsingProcess parsingProcess) {
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
    public boolean startsWithWhitespace(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isCharValid(ALLOWED_WHITESPACES::contains);
    }

    /**
     * Creates a {@link JsonNodeBuilder} for a <code>JSON</code> {@link String}.
     *
     * @param parsingProcess a <code>JSON</code> {@link String} parsing process to create {@link JsonNodeBuilder} for
     * @return {@link JsonNodeBuilder} object able to parse the given <code>JSON</code> {@link String} or {@code null} if there is none
     */
    private JsonNodeBuilder<?, ?> createJsonNodeBuilder(final JsonParsingProcess parsingProcess) {
        return createJsonNodeBuilders().stream()
                .filter(builder -> builder.canParseJson(parsingProcess))
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
}
