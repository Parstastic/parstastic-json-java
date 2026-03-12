package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;
import java.util.function.Predicate;

public class ValidateCharacterStep extends JsonParsingStep {
    private final Predicate<Character> validator;

    public ValidateCharacterStep(final Predicate<Character> validator) {
        super();
        this.validator = validator;
    }

    public ValidateCharacterStep(final char character) {
        this(c -> c == character);
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        if (parsingProcess.isCharValid(this.validator)) {
            return Optional.empty();
        } else {
            return Optional.of(new JsonParsingResult.JsonParsingResultError(
                    "The character to validate was not found or invalid.",
                    parsingProcess
            ));
        }
    }
}
