package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;
import java.util.function.Predicate;

public class WhileLoopStep extends LoopStep {
    private final Predicate<JsonParsingProcess> continueCriteria;

    public WhileLoopStep(final JsonParsingStep instruction,
                         final Predicate<JsonParsingProcess> continueCriteria) {
        super(instruction);
        this.continueCriteria = continueCriteria;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        while (this.continueCriteria.test(parsingProcess)) {
            final Optional<JsonParsingResult.JsonParsingResultError> result = this.instruction.execute(parsingProcess);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }
}
