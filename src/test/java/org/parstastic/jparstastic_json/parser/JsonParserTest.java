package org.parstastic.jparstastic_json.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.parser.exceptions.InvalidJsonException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class JsonParserTest<J extends JsonParticle, E extends InvalidJsonException, P extends JsonParser<J, E>> {
    private P parser;

    @BeforeEach
    void setUp() {
        this.parser = getParser();
    }

    protected abstract P getParser();

    protected abstract Class<J> getParticleType();

    protected abstract Class<E> getExceptionType();

    protected abstract List<String> getValidJsonStrings();

    protected abstract List<String> getInvalidJsonStrings();

    @Nested
    class parseJson {
        @Test
        void valid() {
            final Class<?> particleType = getParticleType();
            final List<String> valid = getValidJsonStrings();
            for (final String string : valid) {
                assertThat(parser.parseJson(string))
                        .isInstanceOf(particleType);
            }
        }

        @Test
        void invalid() {
            final Class<?> exceptionType = getExceptionType();
            final List<String> invalid = getInvalidJsonStrings();
            for (final String string : invalid) {
                assertThatThrownBy(() -> parser.parseJson(string))
                        .isInstanceOf(exceptionType);
            }
        }
    }
}