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
public class FullStringJsonParser implements IJsonParser<JsonNode, InvalidJsonException> {
    /**
     * Creates a {@link FullStringJsonParser} object.
     */
    public FullStringJsonParser() {
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
     * Creates an instance of a {@link JsonNodeBuilder} {@link Class} using a public default constructor.
     *
     * @param clazz {@link Class} object to create a {@link JsonNodeBuilder} of
     * @return a {@link JsonNodeBuilder} object of given {@code clazz} or {@code null} if any exception occurs.
     */
    @SuppressWarnings("rawtypes")
    private JsonNodeBuilder createJsonNodeBuilder(final Class<? extends JsonNodeBuilder> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (final NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
}
