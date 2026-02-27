package org.parstastic.jparstastic_json.node;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Whitespace extends JsonParticle {
    public enum WhitespaceCharacter {
        SPACE(' '),
        HORIZONTAL_TAB('\t'),
        LINE_FEED('\n'),
        CARRIAGE_RETURN('\r');

        public static final Set<Character> CHARACTERS = Arrays.stream(WhitespaceCharacter.values())
                .map(c -> c.character)
                .collect(Collectors.toUnmodifiableSet());

        public static boolean isWhitespaceCharacter(final char character) {
            return CHARACTERS.contains(character);
        }

        private final char character;

        WhitespaceCharacter(final char character) {
            this.character = character;
        }
    }

    private final String value;

    public Whitespace(final String value) throws JsonParticleInstantiationException {
        super();

        validateNotNullOrThrowInstantiationException(value, "value");

        for (final char c : value.toCharArray()) {
            if (!WhitespaceCharacter.isWhitespaceCharacter(c)) {
                throwInstantiationException("The character \"" + c + "\" is not a valid whitespace character.");
            }
        }

        this.value = value;
    }

    @Override
    public String stringify(final StringifyOptions options) {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }
}
