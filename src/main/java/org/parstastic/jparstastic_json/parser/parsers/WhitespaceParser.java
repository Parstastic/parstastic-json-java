package org.parstastic.jparstastic_json.parser.parsers;

import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.node.Whitespace;
import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.steps.JsonParsingStep;
import org.parstastic.jparstastic_json.parser.steps.ParseCharacterStep;
import org.parstastic.jparstastic_json.parser.steps.WhileLoopStep;

import java.util.LinkedList;
import java.util.List;

public class WhitespaceParser extends JsonParticleParser<Whitespace> {
    private final List<Character> characters;

    public WhitespaceParser() {
        this(null);
    }

    /**
     * Creates a new {@link WhitespaceParser} with given initial {@link Whitespace}.
     *
     * @param whitespace initial {@link Whitespace}, may be {@code null}
     */
    public WhitespaceParser(final Whitespace whitespace) {
        super();
        this.characters = new LinkedList<>();
        if (whitespace != null) {
            for (final char c : whitespace.getValue().toCharArray()) {
                this.characters.add(c);
            }
        }
    }

    @Override
    public boolean canParse(final JsonParsingProcess parsingProcess) {
        return true;
    }

    @Override
    protected JsonParsingStep getStep() {
        return new WhileLoopStep(
                new ParseCharacterStep(this.characters::add),
                p -> p.isCharValid(Whitespace.WhitespaceCharacter::isWhitespaceCharacter)
        );
    }

    @Override
    protected Whitespace create() throws JsonParticleInstantiationException {
        return new Whitespace(joinCharacters(this.characters));
    }
}
