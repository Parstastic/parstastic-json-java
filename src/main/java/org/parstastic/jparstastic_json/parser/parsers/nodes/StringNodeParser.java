package org.parstastic.jparstastic_json.parser.parsers.nodes;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.nodes.StringNode;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.*;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringNodeParser extends JsonNodeParser<StringNode> {
    private final List<Character> characters;

    public StringNodeParser() {
        super();
        this.characters = new LinkedList<>();
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return parsingProcess.isAtChar(StringNode.DELIMITER);
    }

    @Override
    protected JsonParsingStep getStep() {
        return new BlockStep(
                createDelimiterParsingStep(),
                createCharactersParsingStep(),
                createDelimiterParsingStep()
        );
    }

    private JsonParsingStep createDelimiterParsingStep() {
        return new ParseCharacterStep(StringNode.DELIMITER);
    }

    private JsonParsingStep createCharactersParsingStep() {
        return new WhileLoopStep(
                createCharacterParsingStep(),
                p -> !p.isAtChar(StringNode.DELIMITER)
        );
    }

    private JsonParsingStep createCharacterParsingStep() {
        return new OrStep(
                Map.of(
                        p -> p.isAtChar('\\'),
                        new BlockStep(
                                new ParseCharacterStep(this.characters::add),
                                createEscapeTargetsParsingStep()
                        )
                ),
                new ParseCharacterStep(this.characters::add)
        );
    }

    private JsonParsingStep createEscapeTargetsParsingStep() {
        return new OrStep(
                createEscapeTargetsParserMap(),
                createUnicodeParser()
        );
    }

    private Map<Predicate<JsonParsingProcess>, JsonParsingStep> createEscapeTargetsParserMap() {
        return Stream.of(
                        '"',
                        '\\',
                        '/',
                        'b',
                        'f',
                        'n',
                        'r',
                        't'
                )
                .map(c -> new AbstractMap.SimpleEntry<>(
                        (Predicate<JsonParsingProcess>) p -> p.isAtChar(c),
                        new ParseCharacterStep(this.characters::add)
                ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private JsonParsingStep createUnicodeParser() {
        return new BlockStep(
                new ValidateCharacterStep('u'),
                new ParseCharacterStep(this.characters::add),
                new ForLoopStep(
                        new BlockStep(
                                new ValidateCharacterStep(c -> Character.digit(c, 16) >= 0),
                                new ParseCharacterStep(this.characters::add)
                        ),
                        4
                )
        );
    }

    @Override
    protected StringNode create() throws JsonParticleInstantiationException {
        return new StringNode(joinCharacters(this.characters));
    }
}
