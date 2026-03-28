package org.parstastic.jparstastic_json.parser.parsers;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.ParameterDeclarations;
import org.parstastic.jparstastic_json.node.JsonParticle;
import org.parstastic.jparstastic_json.node.JsonParticleInstantiationException;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class JsonParticleParserTest {
    interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    private static Stream<Arguments> buildArgs(
            final ExtensionContext context,
            final ThrowingFunction<JsonParticleParserTest, Map<?, ?>, JsonParticleInstantiationException> transformer
    ) throws JsonParticleInstantiationException {
        final JsonParticleParserTest test = (JsonParticleParserTest) context.getRequiredTestInstance();
        final Map<?, ?> map = transformer.apply(test);
        if (map.isEmpty()) {
            return Stream.of(
                    Arguments.of((Object) null)
            );
        } else {
            return map.entrySet().stream()
                    .map(Arguments::of);
        }
    }

    protected abstract JsonParticleParser<?> getInstance();

    protected abstract Map<String, JsonParticle> getValidTargets() throws JsonParticleInstantiationException;

    protected abstract Map<String, JsonParsingResult.JsonParsingResultError> getInvalidTargets();

    @SafeVarargs
    protected final <T, U> Map<T, U> mergeMaps(final Map<T, U>... maps) {
        return Stream.of(maps)
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v2
                ));
    }

    protected Map<String, JsonParticle> getCanParseValidTargets() throws JsonParticleInstantiationException {
        return getValidTargets();
    }

    static class CanParseValidTargetsSource implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
                final ParameterDeclarations parameters,
                final ExtensionContext context
        ) throws JsonParticleInstantiationException {
            return buildArgs(context, JsonParticleParserTest::getCanParseValidTargets);
        }
    }

    protected Map<String, JsonParsingResult.JsonParsingResultError> getCanParseInvalidTargets() {
        return getInvalidTargets();
    }

    static class CanParseInvalidTargetsSource implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
                final ParameterDeclarations parameters,
                final ExtensionContext context
        ) throws JsonParticleInstantiationException {
            return buildArgs(context, JsonParticleParserTest::getCanParseInvalidTargets);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CanParseValidTargetsSource.class)
    void canParse_true(final Map.Entry<String, JsonParticle> validTarget) {
        if (validTarget == null) {
            return;
        }

        assertThat(getInstance().canParse(validTarget.getKey()))
                .isTrue();
    }

    @ParameterizedTest
    @ArgumentsSource(CanParseInvalidTargetsSource.class)
    void canParse_false(final Map.Entry<String, JsonParsingResult.JsonParsingResultError> invalidTarget) {
        if (invalidTarget == null) {
            return;
        }

        assertThat(getInstance().canParse(invalidTarget.getKey()))
                .isFalse();
    }

    protected Map<String, JsonParticle> getParseValidTargets() throws JsonParticleInstantiationException {
        return getValidTargets();
    }

    static class ParseValidTargetsSource implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
                final ParameterDeclarations parameters,
                final ExtensionContext context
        ) throws JsonParticleInstantiationException {
            return buildArgs(context, JsonParticleParserTest::getParseValidTargets);
        }
    }

    protected Map<String, JsonParsingResult.JsonParsingResultError> getParseInvalidTargets() {
        return getInvalidTargets();
    }

    static class ParseInvalidTargetsSource implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(
                final ParameterDeclarations parameters,
                final ExtensionContext context
        ) throws JsonParticleInstantiationException {
            return buildArgs(context, JsonParticleParserTest::getParseInvalidTargets);
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ParseValidTargetsSource.class)
    void parse_success(final Map.Entry<String, JsonParticle> validTarget)
            throws JsonParsingResult.JsonParsingResultNoSuchElementException {
        if (validTarget == null) {
            return;
        }

        final JsonParsingResult<?> result = getInstance().parse(validTarget.getKey());

        assertThat(result.hasValue())
                .isTrue();
        assertThat(result.getValue())
                .usingRecursiveComparison()
                .isEqualTo(validTarget.getValue());
    }

    @ParameterizedTest
    @ArgumentsSource(ParseInvalidTargetsSource.class)
    void parse_failure(final Map.Entry<String, JsonParsingResult.JsonParsingResultError> invalidTarget)
            throws JsonParsingResult.JsonParsingResultNoSuchElementException {
        if (invalidTarget == null) {
            return;
        }

        final JsonParsingResult<?> result = getInstance().parse(invalidTarget.getKey());

        assertThat(result.hasError())
                .isTrue();
        assertThat(result.getError())
                .usingRecursiveComparison()
                .isEqualTo(invalidTarget.getValue());
        assertThatThrownBy(result::getValue)
                .isInstanceOf(JsonParsingResult.JsonParsingResultNoSuchElementException.class);
    }
}