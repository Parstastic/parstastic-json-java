package org.parstastic.jparstastic_json.parser.parsers;

import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.JsonValue;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.node.nodes.JsonNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.parsers.nodes.*;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JsonValueParser extends JsonParticleParser<JsonValue> {
    private Whitespace leadingWhitespace;
    private JsonNode jsonNode;
    private Whitespace trailingWhitespace;

    public JsonValueParser() {
        this(null);
    }

    /**
     * Creates a new {@link JsonValueParser} with given leading {@link Whitespace}.
     *
     * @param leadingWhitespace leading {@link Whitespace}, may be {@code null}
     */
    public JsonValueParser(final Whitespace leadingWhitespace) {
        super();
        this.leadingWhitespace = leadingWhitespace;
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return new WhitespaceParser().canParse(parsingProcess);
    }

    @Override
    protected JsonParsingStep getStep() {
        return new BlockStep(
                createParseStep(
                        new WhitespaceParser(this.leadingWhitespace),
                        w -> this.leadingWhitespace = w
                ),
                OrStep.elseError(
                        createParsersMap()
                ),
                createParseStep(
                        new WhitespaceParser(),
                        w -> this.trailingWhitespace = w
                )
        );
    }

    private Map<JsonParsingStep, Predicate<JsonParsingProcess>> createParsersMap() {
        return createNodeParsers().stream()
                .map(this::createParserEntry)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<JsonNodeParser<?>> createNodeParsers() {
        return List.of(
                new StringNodeParser(),
                new NumberNodeParser(),
                new ObjectNodeParser(),
                new ArrayNodeParser(),
                new BooleanNodeParser(),
                new NullNodeParser()
        );
    }

    private Map.Entry<JsonParsingStep, Predicate<JsonParsingProcess>> createParserEntry(final JsonNodeParser<?> parser) {
        return new AbstractMap.SimpleEntry<>(
                createParseStep(parser, v -> this.jsonNode = v),
                parser::canParse
        );
    }

    private <J extends JsonParticle> ParseStep<J> createParseStep(final JsonParticleParser<J> parser,
                                                                  final Consumer<J> consumer) {
        return new ParseStep<>(
                parser,
                v -> new ExportStep(() -> {
                    consumer.accept(v);
                    return true;
                })
        );
    }

    @Override
    protected JsonValue create() throws JsonParticleInstantiationException {
        return new JsonValue(this.leadingWhitespace, this.jsonNode, this.trailingWhitespace);
    }
}
