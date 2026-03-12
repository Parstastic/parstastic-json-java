package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;
import java.util.function.Predicate;

public class ParseCharacterStep extends JsonParsingStep {
    private final Predicate<Character> exporter;

    public ParseCharacterStep(final char character) {
        this(c -> c == character);
    }

    public ParseCharacterStep(final Predicate<Character> exporter) {
        super();
        this.exporter = exporter;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        if (parsingProcess.isIndexInJson()) {
            final char c = parsingProcess.getChar();
            if (this.exporter.test(c)) {
                parsingProcess.incrementIndex();
                return Optional.empty();
            } else {
                return Optional.of(new JsonParsingResult.JsonParsingResultError(
                        "The required character to parse was not found.",
                        parsingProcess
                ));
            }
        } else {
            return Optional.of(new JsonParsingResult.JsonParsingResultError(
                    "The JsonParsingProcess has exceeded its JSON's length.",
                    parsingProcess
            ));
        }
    }
}
