package org.parstastic.jparstastic_json.parser.steps;

import org.parstastic.jparstastic_json.parser.JsonParsingProcess;
import org.parstastic.jparstastic_json.parser.JsonParsingResult;

import java.util.Optional;

public class ForLoopStep extends LoopStep {
    private final int numberOfIterations;

    public ForLoopStep(final JsonParsingStep instruction,
                       final int numberOfIterations) {
        super(instruction);
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public Optional<JsonParsingResult.JsonParsingResultError> execute(final JsonParsingProcess parsingProcess) {
        for (int i = 0; i < this.numberOfIterations; i++) {
            final Optional<JsonParsingResult.JsonParsingResultError> result = this.instruction.execute(parsingProcess);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }
}
